package io.zerodi.windbag.core.protocol.epp;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * @author zerodi
 */
public class EppProtocolHandler implements ProtocolHandler {
	private static final Logger logger = LoggerFactory.getLogger(EppProtocolHandler.class);
	private final ServerDetail             serverDetail;
	private final MessageExchange          messageExchange;
	private final ApplicationConfiguration configuration;
	private Bootstrap       bootstrap       = null;
	private Channel         channel         = null;
	private ExecutorService executorService = Executors.newCachedThreadPool();
	private Connection connection;

	private EppProtocolHandler(ServerDetail serverDetail, MessageExchange messageExchange, ApplicationConfiguration configuration) {
		this.serverDetail = serverDetail;
		this.messageExchange = messageExchange;
		this.configuration = configuration;

		bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE,
		                 true);

		bootstrap.handler(ChannelConfigurator.getInstance());

	}

	public static ProtocolHandler getInstance(ServerDetail serverDetail, MessageExchange messageExchange, ApplicationConfiguration configuration) {
		return new EppProtocolHandler(serverDetail, messageExchange, configuration);
	}

	@Override
	public Message connect() {
		if (!isConnected()) {
			final String serverAddress = serverDetail.getServerAddress();
			final int serverPort = serverDetail.getServerPort();

			logger.debug("{}:{} - connecting", serverAddress, serverPort);

			EventLoopGroup group = bootstrap.group();
			if (group == null) {
				EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
				bootstrap.group(eventLoopGroup);
			}

			CountDownLatch countDownLatch = new CountDownLatch(1);
			final ResponseReceiver responseReceiver = ResponseReceiver.getInstance(countDownLatch, configuration);

			ChannelFuture connectionFuture = bootstrap.connect(serverAddress, serverPort);
			connectionFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws
				                                                    Exception {
					channel = future.channel();
					ChannelPipeline pipeline = channel.pipeline();
					pipeline.addLast(responseReceiver);
				}
			});

			try {
				countDownLatch.await(configuration.getConnectionTimeoutSeconds(), TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				logger.error("while connecting to remote server", e);
				throw new RuntimeException(e);
			}

			Message receivedMessage = responseReceiver.getReceivedMessage();
			ConnectionEstablishedMessage establishedConnectionMessage = ConnectionEstablishedMessage.getInstance(receivedMessage.getMessage(),
			                                                                                                     "" + connection.getId());
			messageExchange.postMessage(establishedConnectionMessage);
			return establishedConnectionMessage;
		} else {
			return messageExchange.postMessage(StringMessage.getInstance("server already connected; doing nothing", MessageType.SYSTEM_INFO));
		}
	}

	@Override
	public Message disconnect() {
		if (isConnected()) {
			try {
				executorService.shutdown();

				channel.close().sync();
				channel = null;
			} catch (InterruptedException e) {
				logger.error("while disconnecting from remote server", e);
				throw new RuntimeException(e); // TODO handle better than this.
			}
			return messageExchange.postMessage(StringMessage.getInstance("disconnected", MessageType.SYSTEM_CONNECTION_DISCONNECTED));
		} else {
			return messageExchange.postMessage(StringMessage.getInstance("doing nothing; was not connected", MessageType.SYSTEM_INFO));
		}

	}

	@Override
	public boolean isConnected() {
		return channel != null && channel.isActive();

	}

	@Override
	public Protocol getProtocol() {
		return Protocol.EPP;
	}

	@Override
	public Message sendMessage(final Message message) {
		Preconditions.checkNotNull(message, "message cannot be null!");
		Preconditions.checkArgument(isConnected(), "connection needs to be open and active to send the messages!");

		messageExchange.postMessage(message);
		final CountDownLatch countDownLatch = new CountDownLatch(1);
		final ResponseReceiver responseReceiver = ResponseReceiver.getInstance(countDownLatch, configuration);

		channel.pipeline().addLast("response-receiver", responseReceiver);
		channel.writeAndFlush(message.asByteBuf());

		try {
			countDownLatch.await(configuration.getResponseTimeoutSeconds(), TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			logger.error("while waiting for the response after sending a message", e);
			throw new RuntimeException(e);
		}

		Message receivedMessage = responseReceiver.getReceivedMessage();
		messageExchange.postMessage(receivedMessage);
		return receivedMessage;
	}

	@Override public void setConnection(Connection connection) {
		this.connection = connection;
	}
}

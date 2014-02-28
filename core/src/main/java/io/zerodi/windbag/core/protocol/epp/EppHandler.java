package io.zerodi.windbag.core.protocol.epp;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.*;

/**
 * @author zerodi
 */
public class EppHandler implements Handler {
	private static final Logger logger = LoggerFactory.getLogger(EppHandler.class);

	private final ServerDetail             serverDetail;
	private       ProtocolBootstrap        protocolBootstrap;
	private final ApplicationConfiguration configuration;
	private Channel         channel         = null;
	private MessageExchange messageExchange = MessageExchangeImpl.getInstance();
	private ExecutorService executorService = Executors.newCachedThreadPool();

	private EppHandler(ServerDetail serverDetail,
	                   ProtocolBootstrap protocolBootstrap,
	                   ApplicationConfiguration configuration) {
		this.serverDetail = serverDetail;
		this.protocolBootstrap = protocolBootstrap;
		this.configuration = configuration;
	}

	public static Handler getInstance(ServerDetail serverDetail,
	                                  ProtocolBootstrap protocolBootstrap,
	                                  ApplicationConfiguration configuration) {
		return new EppHandler(serverDetail,
		                      protocolBootstrap,
		                      configuration);
	}

	@Override
	public Message connect() {
		if (!isConnected()) {
			String serverAddress = serverDetail.getServerAddress();
			int serverPort = serverDetail.getServerPort();

			logger.debug("{}:{} - connecting",
			             serverAddress,
			             serverPort);
			Bootstrap bootstrap = protocolBootstrap.getBootstrap();

			EventLoopGroup group = bootstrap.group();
			if (group == null) {
				EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
				bootstrap.group(eventLoopGroup);
			}

			final ResponseReceiver responseReceiver = ResponseReceiver.getInstance(getMessageExchange(),
			                                                                       configuration);
			ChannelFuture connectionFuture = bootstrap.connect(serverAddress,
			                                                   serverPort);
			connectionFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws
				                                                    Exception {
					channel = future.channel();
					ChannelPipeline pipeline = channel.pipeline();
					pipeline.addLast(responseReceiver);
				}
			});

			connectionFuture.awaitUninterruptibly(configuration.getConnectionTimeoutSeconds(),
			                                      TimeUnit.SECONDS);

			Future<Object> submit = executorService.submit(new Callable<Object>() {
				@Override
				public Object call() throws
				                     Exception {
					while (!responseReceiver.ifFinished()) {
						try {
							Thread.sleep(2);
						} catch (InterruptedException e) {
							logger.error("while waiting for response after sending a message",
							             e);
							throw new RuntimeException(e);
						}
					}
					return new Object();
				}
			});

			try {
				submit.get(configuration.getResponseTimeoutSeconds(),
				           TimeUnit.SECONDS);
			} catch (InterruptedException | ExecutionException | TimeoutException e) {
				logger.error("while waiting for the response after sending message",
				             e);
				throw new RuntimeException(e);
			}

			return getMessageExchange().getLastMessage();
		} else {
			// TODO find a way to return completed future.
			return getMessageExchange().postMessage(StringMessage.getInstance("server already connected; doing nothing",
			                                                                  MessageType.SYSTEM));
		}
	}

	@Override
	public boolean isConnected() {
		return channel != null && channel.isActive();

	}

	@Override
	public Message disconnect() {
		if (isConnected()) {
			try {
				executorService.shutdown();

				channel.close()
				       .sync();
				channel = null;
			} catch (InterruptedException e) {
				logger.error("while disconnecting from remote server",
				             e);
				throw new RuntimeException(e); // TODO handle better than this.
			}
			return getMessageExchange().postMessage(StringMessage.getInstance("disconnected",
			                                                                  MessageType.SYSTEM));
		} else {
			return getMessageExchange().postMessage(StringMessage.getInstance("doing nothing; was not connected",
			                                                                  MessageType.SYSTEM));
		}

	}

	@Override
	public Message reconnect() {
		disconnect();
		return connect();
	}

	@Override
	public Message sendMessage(Message message) {
		Preconditions.checkNotNull(message,
		                           "message cannot be null!");
		Preconditions.checkArgument(isConnected(),
		                            "connection needs to be open and active to send the messages!");

		synchronized (this) {
			getMessageExchange().postMessage(message);

			ResponseReceiver responseReceiver = ResponseReceiver.getInstance(getMessageExchange(),
			                                                                 configuration);
			channel.pipeline()
			       .addLast("response-receiver",
			                responseReceiver);
			channel.writeAndFlush(message.asByteBuf());

			while (!responseReceiver.ifFinished()) {
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					logger.error("while waiting for response after sending a message",
					             e);
					throw new RuntimeException(e);
				}
			}
			return getMessageExchange().getLastMessage();
		}
	}

	@Override
	public MessageExchange getMessageExchange() {
		return messageExchange;
	}
}

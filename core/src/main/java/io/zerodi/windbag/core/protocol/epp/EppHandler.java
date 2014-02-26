package io.zerodi.windbag.core.protocol.epp;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;
import io.zerodi.windbag.core.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zerodi
 */
public class EppHandler implements Handler {
	private static final Logger logger = LoggerFactory.getLogger(EppHandler.class);

	private final ServerDetail serverDetail;
	private ProtocolBootstrap protocolBootstrap;
	private Channel channel = null;
	private MessageExchange messageExchange = MessageExchangeImpl.getInstance();

	private EppHandler(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
		this.serverDetail = serverDetail;
		this.protocolBootstrap = protocolBootstrap;
	}

	public static Handler getInstance(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
		return new EppHandler(serverDetail, protocolBootstrap);
	}

	@Override
	public Message connect() {
		if (!isConnected()) {
			String serverAddress = serverDetail.getServerAddress();
			int serverPort = serverDetail.getServerPort();

			logger.debug("{}:{} - connecting", serverAddress, serverPort);
			Bootstrap bootstrap = protocolBootstrap.getBootstrap();

			EventLoopGroup group = bootstrap.group();
			if (group == null) {
				EventLoopGroup eventLoopGroup = new NioEventLoopGroup();
				bootstrap.group(eventLoopGroup);
			}

			final ResponseReceiver responseReceiver = ResponseReceiver.getInstance(getMessageExchange());
			ChannelFuture connectionFuture = bootstrap.connect(serverAddress, serverPort);
			connectionFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					channel = future.channel();
					future.channel().pipeline().addLast(responseReceiver);
				}
			});

			connectionFuture.awaitUninterruptibly(20, TimeUnit.SECONDS);

			while (!responseReceiver.ifFinished()) {
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					logger.error("while waiting for response after sending a message", e);
					throw new RuntimeException(e);
				}
			}
			return getMessageExchange().getLastMessage();
		} else {
			// TODO find a way to return completed future.
			return getMessageExchange().postMessage(StringMessage.getInstance("server already connected; doing nothing", MessageType.SYSTEM));
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
				channel.close().sync();
				channel = null;
			} catch (InterruptedException e) {
				logger.error("while disconnecting from remote server", e);
				throw new RuntimeException(e); // TODO handle better than this.
			}
			return getMessageExchange().postMessage(StringMessage.getInstance("disconnected", MessageType.SYSTEM));
		} else {
			return getMessageExchange().postMessage(StringMessage.getInstance("doing nothing; was not connected", MessageType.SYSTEM));
		}
	}

	@Override
	public Message reconnect() {
		disconnect();
		return connect();
	}

	@Override
	public Message sendMessage(Message message) {
		Preconditions.checkNotNull(message, "message cannot be null!");
		Preconditions.checkArgument(isConnected(), "connection needs to be open and active to send the messages!");

		synchronized (this) {
			getMessageExchange().postMessage(message);

			ResponseReceiver responseReceiver = ResponseReceiver.getInstance(getMessageExchange());
			channel.pipeline().addLast("response-receiver", responseReceiver);
			channel.writeAndFlush(message.asByteBuf());

			while (!responseReceiver.ifFinished()) {
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					logger.error("while waiting for response after sending a message", e);
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

package io.zerodi.windbag.app.protocol.epp;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.protocol.Connection;
import io.zerodi.windbag.app.protocol.Message;
import io.zerodi.windbag.app.protocol.MessageExchange;
import io.zerodi.windbag.app.protocol.MessageExchangeImpl;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;

/**
 * @author zerodi
 */
public class EppConnection implements Connection {
	private static final Logger logger = LoggerFactory.getLogger(EppConnection.class);

	private final ServerDetail serverDetail;
	private ProtocolBootstrap protocolBootstrap;
	private Channel channel = null;
	private MessageExchange messageExchange = MessageExchangeImpl.getInstance();

	private EppConnection(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
		this.serverDetail = serverDetail;
		this.protocolBootstrap = protocolBootstrap;
	}

	public static Connection getInstance(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
		return new EppConnection(serverDetail, protocolBootstrap);
	}

	@Override
	public ChannelFuture connect() {
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

			ChannelFuture connectionFuture = bootstrap.connect(serverAddress, serverPort);
			connectionFuture.addListener(new ChannelFutureListener() {
				@Override
				public void operationComplete(ChannelFuture future) throws Exception {
					channel = future.channel();
					future.channel().pipeline().addLast(ResponseReceiver.getInstance(getMessageExchange()));
				}
			});

			try {
				// EPP Server will be greeting us with a greeting message - let's report that one here
				connectionFuture.await(10, TimeUnit.SECONDS);
			} catch (InterruptedException e) {
				logger.error("while connecting to server", e);
				throw new RuntimeException(e); // TODO created specialized exception type, not the generic RuntimeException.
			}
			return connectionFuture;
		} else {
			// TODO find a way to return completed future.
			return null;
		}
	}

	@Override
	public boolean isConnected() {
		if (channel == null) {
			return false;
		}

		return channel.isActive();
	}

	@Override
	public ChannelFuture disconnect() {
		if (channel != null) {
			ChannelFuture channelFuture;
			try {
				channelFuture = channel.close().sync();
				channel = null;
			} catch (InterruptedException e) {
				logger.error("while disconnecting from remote server", e);
				throw new RuntimeException(e); // TODO handle better than this.
			}
			return channelFuture;
		} else {
			return null;
		}
	}

	@Override
	public ChannelFuture reconnect() {
		disconnect();
		return connect();
	}

	@Override
	public Message sendMessage(Message message) {
		Preconditions.checkNotNull(message, "message cannot be null!");
		Preconditions.checkArgument(isConnected(), "connection needs to be open and active to send the messages!");

		synchronized (this) {
			getMessageExchange().postMessage(message);

			ResponseReceiver responseReceiver = (ResponseReceiver) ResponseReceiver.getInstance(getMessageExchange());
			channel.pipeline().addLast("response-receiver", responseReceiver);
			channel.writeAndFlush(message.asByteBuf());

			while (!responseReceiver.ifFinished()) {
				try {
					Thread.sleep(2);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
			return getMessageExchange().getLastMessage();
		}
	}

	@Override
	public ProtocolBootstrap getProtocolBootstrap() {
		return protocolBootstrap;
	}

	@Override
	public ServerDetail getServerDetail() {
		return serverDetail;
	}

	@Override
	public MessageExchange getMessageExchange() {
		return messageExchange;
	}
}

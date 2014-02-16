package io.zerodi.windbag.app.protocol.epp;

import io.netty.channel.*;
import io.zerodi.windbag.app.protocol.MessageExchange;
import io.zerodi.windbag.app.protocol.MessageExchangeImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.protocol.Connection;
import io.zerodi.windbag.app.protocol.Message;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;

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
                }
            });
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
    public ChannelFuture sendMessage(Message message) {
        logger.debug("sending message...");

        channel.pipeline().addLast("response-receiver", ResponseReceiver.getInstance(message));
        return channel.writeAndFlush(message.asByteBuf());
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

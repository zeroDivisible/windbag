package io.zerodi.windbag.app.client.protocol.epp;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.client.protocol.Connection;
import io.zerodi.windbag.app.client.protocol.Message;
import io.zerodi.windbag.app.client.registry.ProtocolBootstrap;

/**
 * @author zerodi
 */
public class EppConnection implements Connection {
    private static final Logger logger = LoggerFactory.getLogger(EppConnection.class);

    private final ServerDetail serverDetail;
    private ProtocolBootstrap protocolBootstrap;
    private boolean connected = false;
    private Channel channel = null;

    private final Object connectionLock = new Object();

    private EppConnection(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
        this.serverDetail = serverDetail;
        this.protocolBootstrap = protocolBootstrap;
    }

    public static Connection getInstance(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
        return new EppConnection(serverDetail, protocolBootstrap);
    }

    @Override
    public ChannelFuture connect() {
        synchronized (connectionLock) {
            if (!connected) {
                String serverAddress = serverDetail.getServerAddress();
                int serverPort = serverDetail.getServerPort();

                logger.debug("{}:{} - connecting", serverAddress, serverPort);
                EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

                Bootstrap bootstrap = protocolBootstrap.getBootstrap();
                bootstrap.group(eventLoopGroup);

                ChannelFuture connectionFuture = null;
                try {
                    connectionFuture = bootstrap.connect(serverAddress, serverPort).sync();
                    channel = connectionFuture.channel();
                    connected = true;
                } catch (InterruptedException e) {
                    logger.error("cannot connect to EPP server", e);
                }
                return connectionFuture;
            } else {
                // TODO find a way to return completed future.
                return null;
            }
        }
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public ChannelFuture disconnect() {
        // TODO Implement
        logger.debug("disconnecting");
        if (channel != null) {
            channel.close();
        }

        connected = false;

        return null;
    }

    @Override
    public ChannelFuture sendMessage(Message message) {
        logger.debug("sending message...");

        // TODO test write, fix it later
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes("X".getBytes(CharsetUtil.UTF_8));

        try {
            ChannelFuture sendingFuture = channel.writeAndFlush(byteBuf).sync();
            return sendingFuture;
        } catch (InterruptedException e) {
            logger.error("while sending message", e);
            return null;
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
}

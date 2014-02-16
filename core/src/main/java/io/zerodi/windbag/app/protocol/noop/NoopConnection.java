package io.zerodi.windbag.app.protocol.noop;

import io.netty.channel.ChannelFuture;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.protocol.Connection;
import io.zerodi.windbag.app.protocol.Message;
import io.zerodi.windbag.app.protocol.MessageExchange;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class NoopConnection implements Connection {
    private static final Logger logger = LoggerFactory.getLogger(NoopConnection.class);
    private final ServerDetail serverDetail;
    private final ProtocolBootstrap protocolBootstrap;
    private boolean connected = false;

    private NoopConnection(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
        this.serverDetail = serverDetail;
        this.protocolBootstrap = protocolBootstrap;
    }

    public static NoopConnection getInstance(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
        return new NoopConnection(serverDetail, protocolBootstrap);
    }

    @Override
    public ChannelFuture connect() {
        logger.debug("connecting...");
        connected = true;

        return null;
    }

    @Override
    public boolean isConnected() {
        return connected;
    }

    @Override
    public ChannelFuture disconnect() {
        logger.debug("disconnecting..");

        return null;
    }

    @Override
    public ChannelFuture reconnect() {
        disconnect();
        return connect();
    }

    @Override
    public ChannelFuture sendMessage(Message message) {
        logger.debug("sending message");

        return null;
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
        return null;  //TODO Implement
    }
}

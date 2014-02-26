package io.zerodi.windbag.core.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;

/**
 * Implementation of the {@link io.zerodi.windbag.core.protocol.Connection} interface
 *
 * @author zerodi
 */
public class ConnectionImpl implements Connection {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionImpl.class);

    private final Handler handler;
    private long connectionid;

    private ConnectionImpl(Handler handler) {
        this.handler = handler;
    }

    public static Connection getInstance(Handler handler) {
        return new ConnectionImpl(handler);
    }

    @Override
    public long getId() {
        return connectionid;
    }

    @Override
    public void setId(long connectionid) {
        this.connectionid = connectionid;
    }

    @Override
    public Handler getHandler() {
        return handler;
    }

    @Override
    public ProtocolBootstrap getProtocolBootstrap() {
        return null; // TODO Implement
    }

    @Override
    public ServerDetail getServerDetail() {
        return null; // TODO Implement
    }

    @Override
    public MessageExchange getMessageExchange() {
        return null; // TODO Implement
    }
}

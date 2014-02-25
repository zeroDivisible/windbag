package io.zerodi.windbag.core.protocol.noop;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.ConnectionFactory;

/**
 * @author zerodi
 */
public class NoopConnectionFactory implements ConnectionFactory {

    private static final NoopProtocolBootstrap NOOP_PROTOCOL_BOOTSTRAP = NoopProtocolBootstrap.getInstance();

    private NoopConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        return new NoopConnectionFactory();
    }

    @Override
    public Connection newConnection(ServerDetail serverDetail) {
        return NoopConnection.getInstance(serverDetail, NOOP_PROTOCOL_BOOTSTRAP);
    }
}

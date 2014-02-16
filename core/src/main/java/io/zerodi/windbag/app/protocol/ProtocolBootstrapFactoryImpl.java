package io.zerodi.windbag.app.protocol;

import java.util.HashMap;

import com.google.common.base.Preconditions;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.protocol.epp.EppConnectionFactory;
import io.zerodi.windbag.app.protocol.noop.NoopConnectionFactory;
import io.zerodi.windbag.core.Protocol;

/**
 * @author zerodi
 */
public class ProtocolBootstrapFactoryImpl {
    private static final HashMap<Protocol, ConnectionFactory> bootstrapFactories = new HashMap<>();

    static {
        bootstrapFactories.put(Protocol.NOOP, NoopConnectionFactory.getInstance());
        bootstrapFactories.put(Protocol.EPP, EppConnectionFactory.getInstance());
    }

    private ProtocolBootstrapFactoryImpl() {
    }

    public static ProtocolBootstrapFactoryImpl getInstance() {
        return new ProtocolBootstrapFactoryImpl();
    }

    public Connection createConnection(ServerDetail serverDetail) {
        Preconditions.checkNotNull(serverDetail, "serverDetail cannot be null!");
        Preconditions.checkNotNull(serverDetail.getProtocol(), "serverDetail.getProtocol() cannot return null!");

        Protocol protocol = serverDetail.getProtocol();
        ConnectionFactory connectionFactory = bootstrapFactories.get(protocol);

        return connectionFactory.newConnection(serverDetail);
    }
}

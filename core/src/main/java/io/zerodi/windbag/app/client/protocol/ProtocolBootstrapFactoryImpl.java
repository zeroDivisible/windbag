package io.zerodi.windbag.app.client.protocol;

import com.google.common.base.Preconditions;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.client.protocol.epp.EppProtocolBootstrapFactory;
import io.zerodi.windbag.app.client.protocol.noop.NoopProtocolBootstrapFactory;
import io.zerodi.windbag.app.client.registery.ProtocolBootstrap;
import io.zerodi.windbag.app.client.registery.ProtocolBootstrapFactory;
import io.zerodi.windbag.core.Protocol;

import java.util.HashMap;

/**
 * @author zerodi
 */
public class ProtocolBootstrapFactoryImpl {
    private static final HashMap<Protocol, ProtocolBootstrapFactory<? extends ProtocolBootstrap>> bootstrapFactories = new HashMap<>();

    static {
        bootstrapFactories.put(Protocol.NOOP, NoopProtocolBootstrapFactory.getInstance());
        bootstrapFactories.put(Protocol.EPP, EppProtocolBootstrapFactory.getInstance());
    }

    private ProtocolBootstrapFactoryImpl() {
    }

    public static ProtocolBootstrapFactoryImpl getInstance() {
        return new ProtocolBootstrapFactoryImpl();
    }

    public ProtocolBootstrap createBootstrap(ServerDetail serverDetail) {
        Preconditions.checkNotNull(serverDetail, "serverDetail cannot be null!");
        Preconditions.checkNotNull(serverDetail.getProtocol(), "serverDetail.getProtocol() cannot return null!");

        Protocol protocol = serverDetail.getProtocol();
        ProtocolBootstrap protocolBootstrap = bootstrapFactories.get(protocol).newInstance();

        return protocolBootstrap;
    }
}

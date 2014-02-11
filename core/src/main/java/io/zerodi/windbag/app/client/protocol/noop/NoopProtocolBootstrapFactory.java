package io.zerodi.windbag.app.client.protocol.noop;

import io.zerodi.windbag.app.client.registry.ProtocolBootstrap;
import io.zerodi.windbag.app.client.protocol.ProtocolBootstrapFactory;

/**
 * @author zerodi
 */
public class NoopProtocolBootstrapFactory implements ProtocolBootstrapFactory<NoopProtocolBootstrap> {

    private static final NoopProtocolBootstrap NOOP_PROTOCOL_BOOTSTRAP = NoopProtocolBootstrap.getInstance();

    private NoopProtocolBootstrapFactory() {
    }

    public static ProtocolBootstrapFactory<NoopProtocolBootstrap> getInstance() {
        return new NoopProtocolBootstrapFactory();
    }

    /**
     * @return cached instance of {@link io.zerodi.windbag.app.client.protocol.noop.NoopProtocolBootstrap}
     */
    @Override
    public ProtocolBootstrap newInstance() {
        return NOOP_PROTOCOL_BOOTSTRAP;
    }
}

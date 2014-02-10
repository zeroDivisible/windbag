package io.zerodi.windbag.app.client.protocol.noop;

import io.zerodi.windbag.app.client.registery.ProtocolBootstrapFactory;

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

    @Override
    public NoopProtocolBootstrap newInstance() {
        return NOOP_PROTOCOL_BOOTSTRAP;
    }
}

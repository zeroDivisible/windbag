package io.zerodi.windbag.app.client.protocol.noop;

import io.netty.bootstrap.Bootstrap;
import io.zerodi.windbag.app.client.registery.ProtocolBootstrap;
import io.zerodi.windbag.core.Protocol;

/**
 * Helper implementation of {@link io.zerodi.windbag.app.client.registery.ProtocolBootstrap}, designed to do nothing.
 *
 * @author zerodi
 */
public class NoopProtocolBootstrap implements ProtocolBootstrap {

    private Bootstrap bootstrap;

    private NoopProtocolBootstrap() {
        bootstrap = new Bootstrap();
    }

    public static NoopProtocolBootstrap getInstance() {
        return new NoopProtocolBootstrap();
    }

    @Override
    public Protocol getProtocol() {
        return Protocol.NOOP;
    }

    @Override
    public Bootstrap getBootstrap() {
        return bootstrap;
    }
}

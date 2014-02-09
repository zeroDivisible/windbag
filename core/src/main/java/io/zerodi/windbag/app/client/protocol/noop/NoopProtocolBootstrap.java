package io.zerodi.windbag.app.client.protocol.noop;

import io.netty.bootstrap.Bootstrap;
import io.zerodi.windbag.app.client.registery.ProtocolBootstrap;
import io.zerodi.windbag.core.Protocol;

/**
 * @author zerodi
 */
public class NoopProtocolBootstrap implements ProtocolBootstrap {

    private NoopProtocolBootstrap() {
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
        return new Bootstrap();
    }
}

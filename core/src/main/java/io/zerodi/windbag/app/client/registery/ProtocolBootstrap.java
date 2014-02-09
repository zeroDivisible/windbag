package io.zerodi.windbag.app.client.registery;

import io.netty.bootstrap.Bootstrap;
import io.zerodi.windbag.core.Protocol;

/**
 * @author zerodi
 */
public interface ProtocolBootstrap {

    public Protocol getProtocol();

    public Bootstrap getBootstrap();
}

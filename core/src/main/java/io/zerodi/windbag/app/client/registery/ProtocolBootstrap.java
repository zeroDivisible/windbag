package io.zerodi.windbag.app.client.registery;

import io.netty.bootstrap.Bootstrap;
import io.zerodi.windbag.core.Protocol;

/**
 * @author zerodi
 */
public interface ProtocolBootstrap {

    /**
     * @return obtains the associated protocol for this bootstrap
     */
    public Protocol getProtocol();

    /**
     * @return obtains the {@link io.netty.bootstrap.Bootstrap} associated with this protocol (underlying class which netty uses to abstract
     *         some of the communication logic)
     */
    public Bootstrap getBootstrap();
}

package io.zerodi.windbag.app.client.protocol;

import io.zerodi.windbag.app.client.registry.ProtocolBootstrap;

/**
 * @author zerodi
 */
public interface ProtocolBootstrapFactory<T extends ProtocolBootstrap> {

    public ProtocolBootstrap newInstance();
}

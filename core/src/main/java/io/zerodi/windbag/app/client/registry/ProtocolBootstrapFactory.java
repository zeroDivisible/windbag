package io.zerodi.windbag.app.client.registry;

/**
 * @author zerodi
 */
public interface ProtocolBootstrapFactory<T extends ProtocolBootstrap> {

    public ProtocolBootstrap newInstance();
}

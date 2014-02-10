package io.zerodi.windbag.app.client.registery;

/**
 * @author zerodi
 */
public interface ProtocolBootstrapFactory<T extends ProtocolBootstrap> {

    public T newInstance();
}

package io.zerodi.windbag.app.client.protocol.epp;

import io.zerodi.windbag.app.client.registery.ProtocolBootstrapFactory;

/**
 * @author zerodi
 */
public class EppProtocolBootstrapFactory implements ProtocolBootstrapFactory<EppProtocolBootstrap> {

    private EppProtocolBootstrapFactory() {
    }

    public static ProtocolBootstrapFactory<EppProtocolBootstrap> getInstance() {
        return new EppProtocolBootstrapFactory();
    }


    @Override
    public EppProtocolBootstrap newInstance() {
        return EppProtocolBootstrap.getInstance();
    }
}

package io.zerodi.windbag.app.client.protocol.epp;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.client.protocol.Connection;
import io.zerodi.windbag.app.client.protocol.ConnectionFactory;

/**
 * @author zerodi
 */
public class EppConnectionFactory implements ConnectionFactory {

    private EppConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        return new EppConnectionFactory();
    }

    @Override
    public Connection newConnection(ServerDetail serverDetail) {
        return EppConnection.getInstance(serverDetail, EppProtocolBootstrap.getInstance());
    }
}

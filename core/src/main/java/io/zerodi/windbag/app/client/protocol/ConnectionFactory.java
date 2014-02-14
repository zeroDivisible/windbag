package io.zerodi.windbag.app.client.protocol;


import io.zerodi.windbag.api.representations.ServerDetail;

/**
 * @author zerodi
 */
public interface ConnectionFactory {

    public Connection newConnection(ServerDetail serverDetail);
}

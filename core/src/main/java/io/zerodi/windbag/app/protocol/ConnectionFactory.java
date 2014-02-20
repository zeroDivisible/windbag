package io.zerodi.windbag.app.protocol;


import io.zerodi.windbag.api.representations.ServerDetail;

/**
 * @author zerodi
 */
public interface ConnectionFactory {

  public Connection newConnection(ServerDetail serverDetail);
}

package io.zerodi.windbag.core.protocol;


import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;

/**
 * @author zerodi
 */
public interface ConnectionFactory {

	public Connection newConnection(ServerDetail serverDetail, ApplicationConfiguration applicationConfiguration);
}

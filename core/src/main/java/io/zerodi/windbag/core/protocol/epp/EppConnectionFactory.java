package io.zerodi.windbag.core.protocol.epp;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.ConnectionFactory;
import io.zerodi.windbag.core.protocol.ConnectionImpl;
import io.zerodi.windbag.core.protocol.Handler;

/**
 * Factory which creates connections initialized with {@link io.zerodi.windbag.core.protocol.epp.EppHandler}, so they can speak to
 * EPP servers.
 *
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
		EppProtocolBootstrap protocolBootstrap = EppProtocolBootstrap.getInstance();
		Handler eppHandler = EppHandler.getInstance(serverDetail, protocolBootstrap);
		return ConnectionImpl.getInstance(eppHandler, serverDetail, protocolBootstrap);
	}
}

package io.zerodi.windbag.core.protocol.noop;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.ConnectionFactory;
import io.zerodi.windbag.core.protocol.ConnectionImpl;

/**
 * @author zerodi
 */
public class NoopConnectionFactory implements ConnectionFactory {

	private static final NoopProtocolBootstrap NOOP_PROTOCOL_BOOTSTRAP = NoopProtocolBootstrap.getInstance();

	private NoopConnectionFactory() {
	}

	public static ConnectionFactory getInstance() {
		return new NoopConnectionFactory();
	}

	@Override
	public Connection newConnection(ServerDetail serverDetail,
	                                ApplicationConfiguration applicationConfiguration) {
		NoopHandler noopHandler = NoopHandler.getInstance(serverDetail,
		                                                  NOOP_PROTOCOL_BOOTSTRAP);
		return ConnectionImpl.getInstance(noopHandler,
		                                  serverDetail,
		                                  NOOP_PROTOCOL_BOOTSTRAP);
	}
}

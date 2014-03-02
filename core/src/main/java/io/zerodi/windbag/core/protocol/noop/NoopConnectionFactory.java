package io.zerodi.windbag.core.protocol.noop;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.*;

/**
 * @author zerodi
 */
public class NoopConnectionFactory implements ConnectionFactory {

	private NoopConnectionFactory() {
	}

	public static ConnectionFactory getInstance() {
		return new NoopConnectionFactory();
	}

	@Override
	public Connection newConnection(ServerDetail serverDetail,
	                                ApplicationConfiguration applicationConfiguration) {
		MessageExchange messageExchange = MessageExchangeImpl.getInstance();

		NoopHandler noopHandler = NoopHandler.getInstance(serverDetail);

		return ConnectionImpl.getInstance(noopHandler,
		                                  messageExchange,
		                                  serverDetail);

	}
}

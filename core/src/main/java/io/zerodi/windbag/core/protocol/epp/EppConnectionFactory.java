package io.zerodi.windbag.core.protocol.epp;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.*;

/**
 * Factory which creates connections initialized with {@link io.zerodi.windbag.core.protocol.epp.EppHandler}, so they can speak to EPP
 * servers.
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
	public Connection newConnection(ServerDetail serverDetail,
	                                ApplicationConfiguration configuration) {

		MessageExchange messageExchange = MessageExchangeImpl.getInstance();
		Handler eppHandler = EppHandler.getInstance(serverDetail,
		                                            messageExchange,
		                                            configuration);

		return ConnectionImpl.getInstance(eppHandler,
		                                  messageExchange,
		                                  serverDetail);
	}
}

package io.zerodi.windbag.core.protocol;

import com.google.common.base.Preconditions;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.epp.EppConnectionFactory;
import io.zerodi.windbag.core.protocol.noop.NoopConnectionFactory;

import java.util.HashMap;

/**
 * @author zerodi
 */
public class BootstrappedConnectionFactory {
	private static final HashMap<Protocol, ConnectionFactory> bootstrapFactories = new HashMap<>();

	static {
		bootstrapFactories.put(Protocol.NOOP,
		                       NoopConnectionFactory.getInstance());
		bootstrapFactories.put(Protocol.EPP,
		                       EppConnectionFactory.getInstance());
	}

	private final ApplicationConfiguration configuration;

	private BootstrappedConnectionFactory(ApplicationConfiguration configuration) {
		this.configuration = configuration;
	}

	public static BootstrappedConnectionFactory getInstance(ApplicationConfiguration configuration) {
		return new BootstrappedConnectionFactory(configuration);
	}

	public Connection createConnection(ServerDetail serverDetail) {
		Preconditions.checkNotNull(serverDetail,
		                           "serverDetail cannot be null!");
		Preconditions.checkNotNull(serverDetail.getProtocol(),
		                           "serverDetail.getProtocol() cannot return null!");

		Protocol protocol = serverDetail.getProtocol();
		ConnectionFactory connectionFactory = bootstrapFactories.get(protocol);

		return connectionFactory.newConnection(serverDetail,
		                                       configuration);
	}
}

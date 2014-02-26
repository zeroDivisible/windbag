package io.zerodi.windbag.core.protocol;

import com.google.common.base.Preconditions;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.protocol.epp.EppConnectionFactory;
import io.zerodi.windbag.core.protocol.noop.NoopConnectionFactory;
import io.zerodi.windbag.core.Protocol;

import java.util.HashMap;

/**
 * @author zerodi
 */
public class BootstrappedConnectionFactory {
	private static final HashMap<Protocol, ConnectionFactory> bootstrapFactories = new HashMap<>();

	static {
		bootstrapFactories.put(Protocol.NOOP, NoopConnectionFactory.getInstance());
		bootstrapFactories.put(Protocol.EPP, EppConnectionFactory.getInstance());
	}

	private BootstrappedConnectionFactory() {
	}

	public static BootstrappedConnectionFactory getInstance() {
		return new BootstrappedConnectionFactory();
	}

	public Connection createConnection(ServerDetail serverDetail) {
		Preconditions.checkNotNull(serverDetail, "serverDetail cannot be null!");
		Preconditions.checkNotNull(serverDetail.getProtocol(), "serverDetail.getProtocol() cannot return null!");

		Protocol protocol = serverDetail.getProtocol();
		ConnectionFactory connectionFactory = bootstrapFactories.get(protocol);

		return connectionFactory.newConnection(serverDetail);
	}
}

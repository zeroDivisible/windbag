package io.zerodi.windbag.app.registry;

import com.google.common.base.Preconditions;
import com.yammer.dropwizard.lifecycle.Managed;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.ConnectionFactoryRegistry;
import io.zerodi.windbag.core.protocol.Handler;
import io.zerodi.windbag.core.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.concurrent.atomic.AtomicLong;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 *
 * @author zerodi
 */
public class ConnectionRegistryImpl implements Managed, ConnectionRegistry {

	private static final Logger logger = LoggerFactory.getLogger(ConnectionRegistryImpl.class);
	private final ConnectionFactoryRegistry connectionFactoryRegistry;

	private AtomicLong                    connectionIdGenerator = new AtomicLong();
	private Map<String, List<Connection>> connectionHashMap     = new HashMap<>();

	private ConnectionRegistryImpl(ConnectionFactoryRegistry connectionFactoryRegistry) {
		this.connectionFactoryRegistry = connectionFactoryRegistry;
	}

	public static ConnectionRegistryImpl getInstance(ConnectionFactoryRegistry connectionFactoryRegistry) {
		return new ConnectionRegistryImpl(connectionFactoryRegistry);
	}

	@Override
	public void start() throws
	                    Exception {
		logger.info("starting channel registry");
	}

	@Override
	public void stop() throws
	                   Exception {
		logger.info("stopping channel registry");

		Collection<List<Connection>> values = connectionHashMap.values();
		for (List<Connection> connections : values) {
			for (Connection connection : connections) {
				Handler handler = connection.getHandler();

				if (handler != null) {
					if (handler.isConnected()) {
						handler.disconnect();
					}
				}
			}
		}
	}

	@Override
	public void registerConnection(Connection connection) {
		Preconditions.checkNotNull(connection, "connection cannot be null!");
		Preconditions.checkNotNull(connection.getHandler(), "connection.getHandler() cannot be null!");
		Preconditions.checkArgument(connection.getId() == 0, "connection.getId() == 0 is not fulfilled, cannot register connection twice!");

		ServerDetail serverDetail = connection.getServerDetail();
		Preconditions.checkNotNull(serverDetail, "connection.getServerDetail() cannot be null!");
		String serverId = serverDetail.getName();
		Preconditions.checkNotNull(serverId, "clientConnection.getServerDetail().getName() cannot be null!");

		List<Connection> connections = connectionHashMap.get(serverId);
		if (connections == null) {
			connections = new ArrayList<>();
			connectionHashMap.put(serverId,
			                      connections);
		}

		connection.setId(connectionIdGenerator.incrementAndGet());
		connections.add(connection);
	}

	@Override
	public Message disconnectAndDeregister(Connection connection) {
		ServerDetail detail = connection.getServerDetail();
		List<Connection> connections = getAllForServer(detail.getName());
		connections.remove(connection);

		return connection.getHandler()
		                 .disconnect();
	}

	@Override
	public Connection createAndRegisterConnection(ServerDetail serverDetail) {
		Connection connection = connectionFactoryRegistry.createConnection(serverDetail);
		registerConnection(connection);

		return connection;
	}

	@Override
	public List<Connection> getAllForServer(String serverId) {
		Preconditions.checkNotNull(serverId,
		                           "serverId cannot be null!");

		if (!connectionHashMap.containsKey(serverId)) {
			connectionHashMap.put(serverId,
			                      new ArrayList<Connection>());
		}

		return connectionHashMap.get(serverId);
	}

	@Override
	public Connection getForServerWithId(String serverId,
	                                     long connectionId) {
		List<Connection> allConnectionsForServer = connectionHashMap.get(serverId);
		for (Connection connection : allConnectionsForServer) {
			if (connection.getId() == connectionId) {
				return connection;
			}
		}

		return null;
	}

}

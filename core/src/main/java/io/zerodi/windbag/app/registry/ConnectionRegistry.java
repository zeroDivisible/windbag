package io.zerodi.windbag.app.registry;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.Message;

import java.util.List;

/**
 * Interface for registries of channels
 *
 * @author zerodi
 */
interface ConnectionRegistry {

	public void registerConnection(Connection connection);

	public Message disconnectAndDeregister(Connection connection);

	public Connection createAndRegisterConnection(ServerDetail serverDetail);

	public List<Connection> getAllForServer(String serverId);

	public Connection getForServerWithId(String serverId,
	                                     long connectionId);

}

package io.zerodi.windbag.app.registry;

import io.zerodi.windbag.core.protocol.Connection;

import java.util.List;

/**
 * Interface for registries of channels
 *
 * @author zerodi
 */
public interface ConnectionRegistry {

	public void registerConnection(Connection connection);

	public List<Connection> getAllForServer(String serverId);

	public Connection getForServerWithId(String serverId, long connectionId);
}

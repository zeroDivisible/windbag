package io.zerodi.windbag.app.client.registry;

import io.zerodi.windbag.app.client.protocol.Connection;

/**
 * Interface for registries of channels
 *
 * @author zerodi
 */
public interface ChannelRegistry {

    /**
     * Registers {@link io.netty.bootstrap.Bootstrap}, assigning it to a name;
     */
    public void registerConnection(Connection connection);

    /**
     * @param serverId
     *            for which we would like to return the bootstrap
     * @return {@link io.netty.bootstrap.Bootstrap} associated with given serverId.
     */
    public Connection getConnection(String serverId);
}

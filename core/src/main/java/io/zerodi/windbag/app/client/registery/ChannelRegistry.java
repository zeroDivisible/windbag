package io.zerodi.windbag.app.client.registery;

import io.netty.bootstrap.Bootstrap;

/**
 * Interface for registries of channels
 * @author zerodi
 */
public interface ChannelRegistry {

    /**
     * Registers {@link io.netty.bootstrap.Bootstrap}, assigning it to a name;
     *
     * @param serverId needs to be unique
     * @param bootstrap
     */
    public void registerChannel(String serverId, ProtocolBootstrap bootstrap);

    /**
     *
     * @param serverId for which we would like to return the bootstrap
     * @return {@link io.netty.bootstrap.Bootstrap} associated with given serverId.
     */
    public ProtocolBootstrap getChannelBootstrap(String serverId);
}

package io.zerodi.windbag.app.client.netty;

import io.netty.bootstrap.Bootstrap;

/**
 * Interface for registries of channels
 * @author zerodi
 */
public interface ChannelRegistry {

    /**
     * Registers {@link io.netty.bootstrap.Bootstrap}, assigning it to a name;
     *
     * @param serverId
     * @param bootstrap
     */
    public void registerChannel(String serverId, Bootstrap bootstrap);
}

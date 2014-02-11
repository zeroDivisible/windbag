package io.zerodi.windbag.app.client.registry;

/**
 * Interface for registries of channels
 * @author zerodi
 */
public interface ChannelRegistry {

    /**
     * Registers {@link io.netty.bootstrap.Bootstrap}, assigning it to a name;
     *
     * @param clientConnection {@link io.zerodi.windbag.app.client.registry.ClientConnection} to connect to server
     */
    public void registerClientConnection(ClientConnection clientConnection);

    /**
     *
     *
     * @param serverId for which we would like to return the bootstrap
     * @return {@link io.netty.bootstrap.Bootstrap} associated with given serverId.
     */
    public ClientConnection getClientConnection(String serverId);
}

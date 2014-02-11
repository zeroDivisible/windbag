package io.zerodi.windbag.app.client.registry;

import io.zerodi.windbag.api.representations.ServerDetail;

/**
 * @author zerodi
 */
public final class ClientConnection {

    private final ServerDetail serverDetail;
    private final ProtocolBootstrap protocolBootstrap;

    private ClientConnection(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
        this.serverDetail = serverDetail;
        this.protocolBootstrap = protocolBootstrap;
    }

    public static ClientConnection getInstance(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
        return new ClientConnection(serverDetail, protocolBootstrap);
    }

    public ProtocolBootstrap getProtocolBootstrap() {
        return protocolBootstrap;
    }

    public ServerDetail getServerDetail() {
        return serverDetail;
    }
}

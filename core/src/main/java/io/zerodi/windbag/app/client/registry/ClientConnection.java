package io.zerodi.windbag.app.client.registry;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;
import io.zerodi.windbag.api.representations.ServerDetail;

/**
 * @author zerodi
 */
public final class ClientConnection {

    private EventLoopGroup eventLoopGroup;
    private final ServerDetail serverDetail;
    private final ProtocolBootstrap protocolBootstrap;

    private ClientConnection(ServerDetail serverDetail, EventLoopGroup eventLoopGroup, ProtocolBootstrap protocolBootstrap) {
        this.serverDetail = serverDetail;
        this.eventLoopGroup = eventLoopGroup;

        this.protocolBootstrap = protocolBootstrap;
        if (eventLoopGroup != null) {
            this.protocolBootstrap.getBootstrap().group(eventLoopGroup);
        }
    }

    public static ClientConnection getInstance(ServerDetail serverDetail, EventLoopGroup eventLoopGroup, ProtocolBootstrap protocolBootstrap) {
        return new ClientConnection(serverDetail, eventLoopGroup, protocolBootstrap);
    }

    public ProtocolBootstrap getProtocolBootstrap() {
        return protocolBootstrap;
    }

    public ServerDetail getServerDetail() {
        return serverDetail;
    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public void replaceEventLookGroup(EventLoopGroup eventLoopGroup) {
        Bootstrap bootstrap = protocolBootstrap.getBootstrap();
        EventLoopGroup group = bootstrap.group();
        if (group != null) {
            group.shutdownGracefully();
        }

        this.eventLoopGroup = eventLoopGroup;
        bootstrap.group(eventLoopGroup);
    }
}

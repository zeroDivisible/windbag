package io.zerodi.windbag.app.client.registry;

import io.netty.channel.EventLoopGroup;

/**
 * Abstracts the details of grouping {@link io.netty.channel.EventLoopGroup} and {@link io.netty.bootstrap.Bootstrap}
 *
 * @author zerodi
 */
public class ChannelDetails {

    private final EventLoopGroup eventLoopGroup;
    private final ClientConnection clientConnection;

    private ChannelDetails(EventLoopGroup eventLoopGroup, ClientConnection clientConnection) {
        this.eventLoopGroup = eventLoopGroup;
        this.clientConnection = clientConnection;
    }

    public static ChannelDetails getInstance(EventLoopGroup eventLoopGroup, ClientConnection clientConnection) {
        return new ChannelDetails(eventLoopGroup, clientConnection);
    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public ClientConnection getClientConnection() {
        return clientConnection;
    }
}

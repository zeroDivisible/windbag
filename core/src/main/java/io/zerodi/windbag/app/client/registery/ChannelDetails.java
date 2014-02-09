package io.zerodi.windbag.app.client.registery;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;

/**
 * Abstracts the details of grouping {@link io.netty.channel.EventLoopGroup} and {@link io.netty.bootstrap.Bootstrap}
 *
 * @author zerodi
 */
public class ChannelDetails {

    private final EventLoopGroup eventLoopGroup;
    private final ProtocolBootstrap bootstrap;

    private ChannelDetails(EventLoopGroup eventLoopGroup, ProtocolBootstrap bootstrap) {
        this.eventLoopGroup = eventLoopGroup;
        this.bootstrap = bootstrap;
    }

    public static ChannelDetails getInstance(EventLoopGroup eventLoopGroup, ProtocolBootstrap bootstrap) {
        return new ChannelDetails(eventLoopGroup, bootstrap);
    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public ProtocolBootstrap getBootstrap() {
        return bootstrap;
    }
}

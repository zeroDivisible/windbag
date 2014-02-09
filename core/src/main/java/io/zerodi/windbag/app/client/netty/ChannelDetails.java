package io.zerodi.windbag.app.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.EventLoopGroup;

/**
 * Abstracts the details of grouping {@link io.netty.channel.EventLoopGroup} and {@link io.netty.bootstrap.Bootstrap}
 *
 * @author zerodi
 */
public class ChannelDetails {

    private final EventLoopGroup eventLoopGroup;
    private final Bootstrap bootstrap;

    private ChannelDetails(EventLoopGroup eventLoopGroup, Bootstrap bootstrap) {
        this.eventLoopGroup = eventLoopGroup;
        this.bootstrap = bootstrap;
    }

    public static ChannelDetails getInstance(EventLoopGroup eventLoopGroup, Bootstrap bootstrap) {
        return new ChannelDetails(eventLoopGroup, bootstrap);
    }

    public EventLoopGroup getEventLoopGroup() {
        return eventLoopGroup;
    }

    public Bootstrap getBootstrap() {
        return bootstrap;
    }
}

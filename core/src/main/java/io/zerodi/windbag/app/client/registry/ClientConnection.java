package io.zerodi.windbag.app.client.registry;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
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

    public ChannelFuture connect() {
        eventLoopGroup = new NioEventLoopGroup();
        replaceEventLookGroup(eventLoopGroup);

        Preconditions.checkNotNull(protocolBootstrap, "protocolBootstrap cannot be null!");
        Preconditions.checkNotNull(protocolBootstrap.getBootstrap(), "protocolBootstrap cannot be null!");
        Preconditions.checkNotNull(serverDetail, "serverDetail cannot be null!");
        Preconditions.checkNotNull(eventLoopGroup, "eventLoopGroup cannot be null!");

        String serverAddress = serverDetail.getServerAddress();
        int serverPort = serverDetail.getServerPort();

        return protocolBootstrap.getBootstrap().connect(serverAddress, serverPort);
    }
}

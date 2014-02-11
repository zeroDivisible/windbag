package io.zerodi.windbag.app.client.registry;

import java.util.Collection;
import java.util.HashMap;

import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.yammer.dropwizard.lifecycle.Managed;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.nio.NioEventLoopGroup;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 *
 * @author zerodi
 */
public class ChannelRegistryImpl implements Managed, ChannelRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ChannelRegistryImpl.class);

    private HashMap<String, ChannelDetails> clientChannelMap = new HashMap<>();

    private ChannelRegistryImpl() {
    }

    public static ChannelRegistryImpl getInstance() {
        return new ChannelRegistryImpl();
    }

    @Override
    public void start() throws Exception {
        logger.info("starting ChannelRegistryImpl");

        ChannelDetails devvm = clientChannelMap.get("devvm");
        Bootstrap bootstrap = devvm.getClientConnection().getProtocolBootstrap().getBootstrap();

        // start the client
        ChannelFuture future = bootstrap.connect("192.168.33.15", 8700).sync();
        future.channel().closeFuture().sync();
    }

    @Override
    public void stop() throws Exception {
        logger.info("stopping ChannelRegistryImpl");

        Collection<ChannelDetails> values = clientChannelMap.values();
        for (ChannelDetails channelDetail : values) {
            EventLoopGroup eventLoopGroup = channelDetail.getEventLoopGroup();

            if (eventLoopGroup != null) {
                eventLoopGroup.shutdownGracefully();
            }
        }
    }

    @Override
    public void registerClientConnection(ClientConnection clientConnection) {
        Preconditions.checkNotNull(clientConnection, "bootstrap cannot be null!");
        String serverId = clientConnection.getServerDetail().getName();
        Preconditions.checkNotNull(serverId, "clientConnection.getServerDetail().getName() cannot be null!");
        Preconditions.checkNotNull(clientConnection.getProtocolBootstrap(), "clientConnection.getProtocolBootstrap() cannot be null!");

        if (clientChannelMap.containsKey(serverId)) {
            throw new RuntimeException("already registered " + serverId);
        }

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        ProtocolBootstrap protocolBootstrap = clientConnection.getProtocolBootstrap();
        protocolBootstrap.getBootstrap().group(eventLoopGroup);
        clientChannelMap.put(serverId, ChannelDetails.getInstance(eventLoopGroup, clientConnection));
    }

    @Override
    public ClientConnection getClientConnection(String serverId) {
        Preconditions.checkNotNull(serverId, "serverId cannot be null!");

        if (!clientChannelMap.containsKey(serverId)) {
            return null;
        }

        return clientChannelMap.get(serverId).getClientConnection();
    }

}

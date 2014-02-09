package io.zerodi.windbag.app.client.registery;

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
import io.zerodi.windbag.app.client.protocol.epp.EppProtocolBootstrap;

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

        // TODO - this will not be needed, right now we are only connecting mostly for the tests.
        EppProtocolBootstrap eppProtocolBootstrap = EppProtocolBootstrap.getInstance();
        Bootstrap bootstrap = eppProtocolBootstrap.getBootstrap();

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
    public void registerChannel(String serverId, ProtocolBootstrap bootstrap) {
        Preconditions.checkNotNull(serverId, "serverId cannot be null!");
        Preconditions.checkNotNull(bootstrap, "bootstrap cannot be null!");
        Preconditions.checkNotNull(bootstrap.getBootstrap(), "bootstrap cannot be null!");

        if (clientChannelMap.containsKey(serverId)) {
            throw new RuntimeException("already registered " + serverId);
        }

        NioEventLoopGroup eventLoopGroup = new NioEventLoopGroup();
        bootstrap.getBootstrap().group(eventLoopGroup);
        clientChannelMap.put(serverId, ChannelDetails.getInstance(eventLoopGroup, bootstrap));
    }

    @Override
    public ProtocolBootstrap getChannelBootstrap(String serverId) {
        Preconditions.checkNotNull(serverId, "serverId cannot be null!");

        if (!clientChannelMap.containsKey(serverId)) {
            return null;
        }

        return clientChannelMap.get(serverId).getBootstrap();
    }

}

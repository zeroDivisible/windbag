package io.zerodi.windbag.app.client.registery;

import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.common.base.Preconditions;
import com.yammer.dropwizard.lifecycle.Managed;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.zerodi.windbag.app.client.protocol.epp.EppClientDecoder;
import io.zerodi.windbag.app.client.protocol.epp.EppClientHandler;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 *
 * @author zerodi
 */
public class ChannelRegistryImpl implements Managed, ChannelRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ChannelRegistryImpl.class);

    private EventLoopGroup eventLoopGroup;
    private HashMap<String, ChannelDetails> clientChannelMap = new HashMap<>();

    private ChannelRegistryImpl() {
    }

    public static ChannelRegistryImpl getInstance() {
        return new ChannelRegistryImpl();
    }

    @Override
    public void start() throws Exception {
        logger.info("starting ChannelRegistryImpl");
        eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(EppClientDecoder.getInstance(), EppClientHandler.getInstance());
                }
            });

            // start the client
            ChannelFuture future = bootstrap.connect("192.168.33.15", 8700).sync();
            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() throws Exception {
        logger.info("stopping ChannelRegistryImpl");

        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    @Override
    public void registerChannel(String serverId, ProtocolBootstrap bootstrap) {
        Preconditions.checkNotNull(serverId, "serverId cannot be null!");
        Preconditions.checkNotNull(bootstrap, "bootstrap cannot be null!");

        if (clientChannelMap.containsKey(serverId)) {
            throw new RuntimeException("already registered " + serverId);
        }

        clientChannelMap.put(serverId, ChannelDetails.getInstance(new NioEventLoopGroup(), bootstrap));
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

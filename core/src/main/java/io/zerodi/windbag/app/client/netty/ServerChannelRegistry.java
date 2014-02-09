package io.zerodi.windbag.app.client.netty;

import com.google.common.base.Preconditions;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.util.CharsetUtil;
import io.zerodi.windbag.app.client.epp.EppClientDecoder;
import io.zerodi.windbag.app.client.epp.EppClientHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.lifecycle.Managed;

import java.util.HashMap;
import java.util.List;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 *
 * @author zerodi
 */
public class ServerChannelRegistry implements Managed, ChannelRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ServerChannelRegistry.class);

    private EventLoopGroup eventLoopGroup;
    private HashMap<String, ChannelDetails> clientChannelMap = new HashMap<>();

    private ServerChannelRegistry() {
    }

    public static ServerChannelRegistry getInstance() {
        return new ServerChannelRegistry();
    }

    @Override
    public void start() throws Exception {
        logger.info("starting ServerChannelRegistry");
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
        logger.info("stopping ServerChannelRegistry");

        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    @Override
    public void registerChannel(String serverId, Bootstrap bootstrap) {
        Preconditions.checkNotNull(serverId, "serverId cannot be null!");
        Preconditions.checkNotNull(bootstrap, "bootstrap cannot be null!");

        if (clientChannelMap.containsKey(serverId)) {
            throw new RuntimeException("already registered " + serverId);
        }

        clientChannelMap.put(serverId, ChannelDetails.getInstance(new NioEventLoopGroup(), bootstrap));
    }

    @Override
    public Bootstrap getChannelBootstrap(String serverId) {
        Preconditions.checkNotNull(serverId, "serverId cannot be null!");

        if (!clientChannelMap.containsKey(serverId)) {
            return null;
        }

        return clientChannelMap.get(serverId).getBootstrap();
    }

}

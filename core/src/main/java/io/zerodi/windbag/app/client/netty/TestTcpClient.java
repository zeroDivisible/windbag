package io.zerodi.windbag.app.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.lifecycle.Managed;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 *
 * @author zerodi
 */
public class TestTcpClient implements Managed {
    private static final Logger logger = LoggerFactory.getLogger(TestTcpClient.class);

    private EventLoopGroup eventLoopGroup;

    private TestTcpClient() {
    }

    public static TestTcpClient getInstance() {
        return new TestTcpClient();
    }

    @Override
    public void start() throws Exception {
        logger.info("starting TestTcpClient");
        eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

            bootstrap.handler(new ChannelInitializer<SocketChannel>() {

                @Override
                protected void initChannel(SocketChannel ch) throws Exception {
                    ch.pipeline().addLast(new EppClientHandler());
                }
            });

            // start the client
            ChannelFuture future = bootstrap.connect("192.168.33.15", 8701);

            future.channel().closeFuture().sync();
        } finally {
            eventLoopGroup.shutdownGracefully();
        }

    }

    @Override
    public void stop() throws Exception {
        logger.info("stopping TestTcpClient");

        if (eventLoopGroup != null) {
            eventLoopGroup.shutdownGracefully();
        }
    }

    public static class EppClientHandler extends ChannelHandlerAdapter {

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf m = (ByteBuf) msg;
            try {
                for (int i = 0; i < m.capacity(); i++) {
                    byte b = m.getByte(i);
                    System.out.print((char) b);
                }
            } catch (Exception e) {
                m.release();
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            logger.error(cause.getMessage(), cause);
            ctx.close();
        }
    }
}

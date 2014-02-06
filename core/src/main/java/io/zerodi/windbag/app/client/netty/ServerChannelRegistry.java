package io.zerodi.windbag.app.client.netty;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.dropwizard.lifecycle.Managed;

import java.util.List;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 *
 * @author zerodi
 */
public class ServerChannelRegistry implements Managed {
    private static final Logger logger = LoggerFactory.getLogger(ServerChannelRegistry.class);

    private EventLoopGroup eventLoopGroup;

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
                    ch.pipeline().addLast(new EppClientDecoder(), new EppClientHandler());
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

    public static class EppClientDecoder extends ByteToMessageDecoder {

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            logger.error(cause.getMessage(), cause);
            ctx.close();
        }

        @Override
        protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
            if (in.readableBytes() < 4) {
                return; // (3)
            }

            out.add(in.readBytes(in.readableBytes())); // (4)
        }
    }

    public static class EppClientHandler extends ChannelHandlerAdapter {
        private ByteBuf buf;

        @Override
        public void handlerAdded(ChannelHandlerContext ctx) {
            buf = ctx.alloc().buffer(4); // (1)
        }

        @Override
        public void handlerRemoved(ChannelHandlerContext ctx) {
            buf.release(); // (1)
            buf = null;
        }

        @Override
        public void channelRead(ChannelHandlerContext ctx, Object msg) {
            ByteBuf m = (ByteBuf) msg;
            buf.writeBytes(m); // (2)
            m.release();

            for (int i = 0; i < buf.readableBytes(); i++) {
                System.out.print((char) buf.readByte());
            }
        }

        @Override
        public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
            logger.error(cause.getMessage(), cause);
            ctx.close();
        }
    }
}

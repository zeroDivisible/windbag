package io.zerodi.windbag.app.client.protocol.epp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.CharsetUtil;

/**
 * @author zerodi
 */
public class EppClientHandler extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(EppClientHandler.class);

    private ByteBuf buf;

    private EppClientHandler() {
    }

    public static EppClientHandler getInstance() {
        return new EppClientHandler();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        buf = ctx.alloc().buffer(1024);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) {
        buf.release();
        buf = null;
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
        ByteBuf m = (ByteBuf) msg;
        buf.writeBytes(m);
        m.release();

        byte[] readableBytes = new byte[buf.readableBytes()];
        buf.readBytes(readableBytes);

        String receivedMessage = new String(readableBytes, CharsetUtil.UTF_8);
        logger.info(receivedMessage);
//        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }
}

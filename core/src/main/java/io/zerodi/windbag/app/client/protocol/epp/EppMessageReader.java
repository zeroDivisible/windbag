package io.zerodi.windbag.app.client.protocol.epp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import io.zerodi.windbag.app.client.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

/**
 * @author zerodi
 */
public class EppMessageReader extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(EppMessageReader.class);
    private ByteBuf buf;

    private EppMessageReader() {
    }

    public static EppMessageReader getInstance() {
        return new EppMessageReader();
    }

    @Override
    public void handlerAdded(ChannelHandlerContext ctx) {
        buf = ctx.alloc().buffer(2048);
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

        Message instance = EppMessage.getInstance(readableBytes);
        logger.debug("{}", instance);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("while reading EPP message", cause);
        ctx.close();
    }
}

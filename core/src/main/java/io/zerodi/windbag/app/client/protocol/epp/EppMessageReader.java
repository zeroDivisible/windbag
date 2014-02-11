package io.zerodi.windbag.app.client.protocol.epp;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

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
    private final BlockingQueue<String> answer = new LinkedBlockingQueue<>();

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

        EppMessage message = (EppMessage) EppMessage.getInstance(readableBytes);
        answer.offer(message.getMessage());
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }

    public String getMessage() {
        boolean interrupted = false;
        for (;;) {
            try {
                String message = answer.take();
                if (interrupted) {
                    Thread.currentThread().interrupt();
                }
                return message;
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
    }
}

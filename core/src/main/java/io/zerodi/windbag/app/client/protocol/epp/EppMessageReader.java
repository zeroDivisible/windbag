package io.zerodi.windbag.app.client.protocol.epp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.zerodi.windbag.app.client.registry.ProtocolBootstrap;

/**
 * @author zerodi
 */
public class EppMessageReader extends ChannelHandlerAdapter {
    private static final Logger logger = LoggerFactory.getLogger(EppMessageReader.class);
    private final ProtocolBootstrap protocolBootstrap;

    private ByteBuf buf;

    private EppMessageReader(ProtocolBootstrap protocolBootstrap) {
        this.protocolBootstrap = protocolBootstrap;
    }

    public static EppMessageReader getInstance(ProtocolBootstrap protocolBootstrap) {
        return new EppMessageReader(protocolBootstrap);
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

        protocolBootstrap.onMessage(EppMessage.getInstance(readableBytes));
//        ctx.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }
}

package io.zerodi.windbag.app.client.protocol.epp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author zerodi
 */
public class EppClientDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(EppClientDecoder.class);

    private EppClientDecoder() {
    }

    public static EppClientDecoder getInstance() {
        return new EppClientDecoder();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error(cause.getMessage(), cause);
        ctx.close();
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }

        int messageLength = in.getInt(0);
        if (in.readableBytes() < messageLength) {
            return;
        } else {
            // we can read a header
            in.readInt();
        }

        // and add the full message to the buffer
        out.add(in.readBytes(messageLength - 4));
    }
}

package io.zerodi.windbag.app.protocol.epp;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @author zerodi
 */
public class EppMessageDecoder extends ByteToMessageDecoder {
    private static final Logger logger = LoggerFactory.getLogger(EppMessageDecoder.class);

    private EppMessageDecoder() {
    }

    public static EppMessageDecoder getInstance() {
        return new EppMessageDecoder();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("while decoding EPP message", cause);
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

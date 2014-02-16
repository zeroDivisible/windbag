package io.zerodi.windbag.app.protocol.epp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.zerodi.windbag.app.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class ResponseReceiver extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseReceiver.class);


    private final Message message;

    private ResponseReceiver(Message message) {
        this.message = message;
    }

    public static ResponseReceiver getInstance(Message message) {
        return new ResponseReceiver(message);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.debug("got {}", msg);
        ctx.pipeline().remove(this);
        message.setResponse(msg);
    }
}

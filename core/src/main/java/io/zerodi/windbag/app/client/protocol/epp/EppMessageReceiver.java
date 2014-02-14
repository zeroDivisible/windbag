package io.zerodi.windbag.app.client.protocol.epp;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zerodi
 */
public class EppMessageReceiver extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(EppMessageReceiver.class);

    private EppMessageReceiver() {
    }

    public static EppMessageReceiver getInstance() {
        return new EppMessageReceiver();
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.debug("received {}", msg);
    }
}

package io.zerodi.windbag.app.client.registry;

import io.netty.channel.ChannelPromise;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author zerodi
 */
public class TestHandler extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(TestHandler.class);

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.debug("received {}", msg);
    }
}

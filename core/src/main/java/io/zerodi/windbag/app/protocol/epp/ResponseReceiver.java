package io.zerodi.windbag.app.protocol.epp;

import io.zerodi.windbag.app.protocol.Message;
import io.zerodi.windbag.app.protocol.MessageType;
import io.zerodi.windbag.app.protocol.StringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.zerodi.windbag.app.protocol.MessageExchange;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author zerodi
 */
public class ResponseReceiver extends SimpleChannelInboundHandler<String> {
    private static final Logger logger = LoggerFactory.getLogger(ResponseReceiver.class);

    private final MessageExchange messageExchange;
    private volatile boolean finished;

    private ResponseReceiver(MessageExchange messageExchange) {
        this.messageExchange = messageExchange;
    }

    public static SimpleChannelInboundHandler<String> getInstance(MessageExchange messageExchange) {
        return new ResponseReceiver(messageExchange);
    }

    @Override
    protected void messageReceived(ChannelHandlerContext ctx, String msg) throws Exception {
        logger.debug("got {}", msg);
        ctx.pipeline().remove(this);

        Message receivedMessage = StringMessage.getInstance(msg, MessageType.INBOUND);
        messageExchange.postMessage(receivedMessage);
        finished = true;
    }

    public boolean ifFinished() {
        return finished;

    }
}

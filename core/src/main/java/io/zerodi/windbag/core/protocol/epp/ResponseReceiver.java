package io.zerodi.windbag.core.protocol.epp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.Message;
import io.zerodi.windbag.core.protocol.MessageType;
import io.zerodi.windbag.core.protocol.StringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.CountDownLatch;

/**
 * @author zerodi
 */
public class ResponseReceiver extends SimpleChannelInboundHandler<String> {
	private static final Logger logger = LoggerFactory.getLogger(ResponseReceiver.class);

	private final CountDownLatch           lock;
	private final ApplicationConfiguration configuration;
	private       Message                  receivedMessage;

	private ResponseReceiver(CountDownLatch lock, ApplicationConfiguration configuration) {
		this.lock = lock;
		this.configuration = configuration;
	}

	public static ResponseReceiver getInstance(CountDownLatch lock, ApplicationConfiguration configuration) {
		return new ResponseReceiver(lock, configuration);
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx, String msg) {
		logger.debug("got {}, handling it and removing itself from pipeline", msg);
		ctx.pipeline().remove(this);

		setReceivedMessage(StringMessage.getInstance(msg, MessageType.INBOUND));
		lock.countDown();
	}

	public Message getReceivedMessage() {
		return receivedMessage;
	}

	public void setReceivedMessage(Message receivedMessage) {
		this.receivedMessage = receivedMessage;
	}
}

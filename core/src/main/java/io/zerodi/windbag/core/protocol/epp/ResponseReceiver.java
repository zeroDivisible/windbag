package io.zerodi.windbag.core.protocol.epp;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.Message;
import io.zerodi.windbag.core.protocol.MessageExchange;
import io.zerodi.windbag.core.protocol.MessageType;
import io.zerodi.windbag.core.protocol.StringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class ResponseReceiver extends SimpleChannelInboundHandler<String> {
	private static final Logger logger = LoggerFactory.getLogger(ResponseReceiver.class);

	private final    MessageExchange          messageExchange;
	private final    ApplicationConfiguration configuration;
	private volatile boolean                  finished;

	private ResponseReceiver(MessageExchange messageExchange,
	                         ApplicationConfiguration configuration) {
		this.messageExchange = messageExchange;
		this.configuration = configuration;
	}

	public static ResponseReceiver getInstance(MessageExchange messageExchange,
	                                           ApplicationConfiguration configuration) {
		return new ResponseReceiver(messageExchange,
		                            configuration);
	}

	@Override
	protected void messageReceived(ChannelHandlerContext ctx,
	                               String msg) throws
	                                           Exception {
		logger.debug("got {}, handling it and removing itself from pipeline",
		             msg);
		ctx.pipeline()
		   .remove(this);

		Message receivedMessage = StringMessage.getInstance(msg,
		                                                    MessageType.INBOUND);
		messageExchange.postMessage(receivedMessage);
		finished = true;
	}

	public boolean ifFinished() {
		return finished;

	}
}

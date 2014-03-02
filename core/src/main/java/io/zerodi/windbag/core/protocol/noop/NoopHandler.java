package io.zerodi.windbag.core.protocol.noop;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.Handler;
import io.zerodi.windbag.core.protocol.Message;
import io.zerodi.windbag.core.protocol.MessageType;
import io.zerodi.windbag.core.protocol.StringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class NoopHandler implements Handler {
	private static final Logger logger = LoggerFactory.getLogger(NoopHandler.class);
	private final ServerDetail serverDetail;
	private boolean connected = false;

	private NoopHandler(ServerDetail serverDetail) {
		this.serverDetail = serverDetail;
	}

	public static NoopHandler getInstance(ServerDetail serverDetail) {
		return new NoopHandler(serverDetail);
	}

	@Override
	public Message connect() {
		logger.debug("connecting...");
		connected = true;

		return StringMessage.getInstance("noop connection started",
		                                 MessageType.SYSTEM);
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public Protocol getProtocol() {
		return Protocol.NOOP;
	}

	@Override
	public Message disconnect() {
		logger.debug("disconnecting..");
		return StringMessage.getInstance("noop connection closed",
		                                 MessageType.SYSTEM);
	}


	@Override
	public Message sendMessage(Message message) {
		logger.debug("sending message");
		return StringMessage.getInstance("noop message sent",
		                                 MessageType.SYSTEM);
	}
}

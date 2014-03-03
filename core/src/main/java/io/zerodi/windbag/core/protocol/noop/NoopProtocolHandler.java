package io.zerodi.windbag.core.protocol.noop;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class NoopProtocolHandler implements ProtocolHandler {
	private static final Logger logger = LoggerFactory.getLogger(NoopProtocolHandler.class);
	private final ServerDetail serverDetail;
	private boolean connected = false;
	private Connection connection = null;

	private NoopProtocolHandler(ServerDetail serverDetail) {
		this.serverDetail = serverDetail;
	}

	public static NoopProtocolHandler getInstance(ServerDetail serverDetail) {
		return new NoopProtocolHandler(serverDetail);
	}

	@Override
	public Message connect() {
		logger.debug("connecting...");
		connected = true;

		return StringMessage.getInstance("noop connection started", MessageType.SYSTEM_CONNECTION_ESTABLISHED);
	}

	@Override
	public Message disconnect() {
		logger.debug("disconnecting..");
		return StringMessage.getInstance("noop connection closed", MessageType.SYSTEM_CONNECTION_DISCONNECTED);
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
	public Message sendMessage(Message message) {
		logger.debug("sending message");
		return StringMessage.getInstance("noop message sent", MessageType.SYSTEM_INFO);
	}

	@Override public void setConnection(Connection connection) {
		this.connection = connection;
	}
}

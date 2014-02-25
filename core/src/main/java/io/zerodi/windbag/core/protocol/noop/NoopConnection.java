package io.zerodi.windbag.core.protocol.noop;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;
import io.zerodi.windbag.core.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class NoopConnection implements Connection {
	private static final Logger logger = LoggerFactory.getLogger(NoopConnection.class);
	private final ServerDetail serverDetail;
	private final ProtocolBootstrap protocolBootstrap;
	private boolean connected = false;

	private NoopConnection(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
		this.serverDetail = serverDetail;
		this.protocolBootstrap = protocolBootstrap;
	}

	public static NoopConnection getInstance(ServerDetail serverDetail, ProtocolBootstrap protocolBootstrap) {
		return new NoopConnection(serverDetail, protocolBootstrap);
	}

	@Override
	public Message connect() {
		logger.debug("connecting...");
		connected = true;

		return StringMessage.getInstance("noop connection started", MessageType.SYSTEM);
	}

	@Override
	public boolean isConnected() {
		return connected;
	}

	@Override
	public Message disconnect() {
		logger.debug("disconnecting..");
		return StringMessage.getInstance("noop connection closed", MessageType.SYSTEM);
	}

	@Override
	public Message reconnect() {
		disconnect();
		return connect();
	}

	@Override
	public Message sendMessage(Message message) {
		logger.debug("sending message");

		return null;
	}

	@Override
	public ProtocolBootstrap getProtocolBootstrap() {
		return protocolBootstrap;
	}

	@Override
	public ServerDetail getServerDetail() {
		return serverDetail;
	}

	@Override
	public MessageExchange getMessageExchange() {
		return null;  //TODO Implement
	}
}

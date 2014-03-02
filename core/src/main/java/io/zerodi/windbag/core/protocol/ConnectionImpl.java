package io.zerodi.windbag.core.protocol;

import com.google.common.base.Preconditions;
import io.zerodi.windbag.api.representations.ServerDetail;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of the {@link io.zerodi.windbag.core.protocol.Connection} interface
 *
 * @author zerodi
 */
public class ConnectionImpl implements Connection {
	private static final Logger logger = LoggerFactory.getLogger(ConnectionImpl.class);

	private final ProtocolHandler protocolHandler;
	private final MessageExchange messageExchange;
	private final ServerDetail    serverDetail;

	private long connectionId;

	private ConnectionImpl(ProtocolHandler protocolHandler, MessageExchange messageExchange, ServerDetail serverDetail) {
		this.protocolHandler = protocolHandler;
		this.messageExchange = messageExchange;
		this.serverDetail = serverDetail;

		protocolHandler.setConnection(this);
	}

	public static Connection getInstance(ProtocolHandler protocolHandler, MessageExchange messageExchange, ServerDetail serverDetail) {
		Preconditions.checkNotNull(protocolHandler, "protocolHandler cannot be null!");

		return new ConnectionImpl(protocolHandler, messageExchange, serverDetail);
	}

	@Override
	public long getId() {
		return connectionId;
	}

	@Override
	public void setId(long connectionId) {
		this.connectionId = connectionId;
	}

	@Override
	public ProtocolHandler getProtocolHandler() {
		return protocolHandler;
	}

	@Override
	public ServerDetail getServerDetail() {
		return serverDetail;
	}

	@Override
	public MessageExchange getMessageExchange() {
		return messageExchange;
	}
}

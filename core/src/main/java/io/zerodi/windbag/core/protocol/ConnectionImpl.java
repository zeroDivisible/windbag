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

	private final Handler         handler;
	private final MessageExchange messageExchange;
	private final ServerDetail    serverDetail;

	private long connectionId;

	private ConnectionImpl(Handler handler, MessageExchange messageExchange, ServerDetail serverDetail) {
		this.handler = handler;
		this.messageExchange = messageExchange;
		this.serverDetail = serverDetail;

		handler.setConnection(this);
	}

	public static Connection getInstance(Handler handler, MessageExchange messageExchange, ServerDetail serverDetail) {
		Preconditions.checkNotNull(handler, "handler cannot be null!");

		return new ConnectionImpl(handler, messageExchange, serverDetail);
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
	public Handler getHandler() {
		return handler;
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

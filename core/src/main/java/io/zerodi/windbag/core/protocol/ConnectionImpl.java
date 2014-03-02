package io.zerodi.windbag.core.protocol;

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

	private final Handler      handler;
	private final ServerDetail serverDetail;

	private long connectionId;

	private ConnectionImpl(Handler handler,
	                       ServerDetail serverDetail) {
		this.handler = handler;
		this.serverDetail = serverDetail;
	}

	public static Connection getInstance(Handler handler,
	                                     MessageExchange messageExchange,
	                                     ServerDetail serverDetail) {
		return new ConnectionImpl(handler,
		                          serverDetail);
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
		return null; // TODO Implement
	}
}

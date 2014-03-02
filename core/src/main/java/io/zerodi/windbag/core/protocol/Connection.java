package io.zerodi.windbag.core.protocol;

import io.zerodi.windbag.api.representations.ServerDetail;

/**
 * Interface abstracting connection to a remote server.
 *
 * @author zerodi
 */
public interface Connection {

	public long getId();

	public void setId(long id);

	public ProtocolHandler getProtocolHandler();

	public ServerDetail getServerDetail();

	public MessageExchange getMessageExchange();
}

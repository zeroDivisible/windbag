package io.zerodi.windbag.core.protocol;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;

/**
 * Interface abstracting connection to a remote server.
 *
 * @author zerodi
 */
public interface Connection {

	public long getId();

	public void setId(long id);

	public Handler getHandler();

	public ProtocolBootstrap getProtocolBootstrap();

	public ServerDetail getServerDetail();

	public MessageExchange getMessageExchange();
}

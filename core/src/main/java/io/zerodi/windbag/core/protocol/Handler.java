package io.zerodi.windbag.core.protocol;

import io.zerodi.windbag.core.Protocol;

/**
 * @author zerodi
 */
public interface Handler {
	/**
	 * @return {@link io.netty.channel.ChannelFuture} returned when opening a channel to remote server.
	 */
	public Message connect();

	/**
	 * @return {@link io.netty.channel.ChannelFuture} returned when closing a channel to remote server.
	 */
	public Message disconnect();

	/**
	 * @return <code>true</code> if channel to remote server is open
	 */
	public boolean isConnected();

	public Protocol getProtocol();

	public Message sendMessage(Message message);
}

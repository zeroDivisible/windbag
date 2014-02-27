package io.zerodi.windbag.core.protocol;

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
	 * @return {@link io.netty.channel.ChannelFuture} returned when reopening a channel to remote server.
	 */
	public Message reconnect();

	/**
	 * @return <code>true</code> if channel to remote server is open
	 */
	public boolean isConnected();

	public Message sendMessage(Message message);

	MessageExchange getMessageExchange();
}

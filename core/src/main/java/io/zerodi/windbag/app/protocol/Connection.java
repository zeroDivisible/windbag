package io.zerodi.windbag.app.protocol;

import io.netty.channel.ChannelFuture;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;

import java.util.concurrent.Future;

/**
 * Interface abstracting connection to a remote server.
 *
 * @author zerodi
 */
public interface Connection {

    /**
     * @return {@link io.netty.channel.ChannelFuture} returned when opening a channel to remote server.
     */
    public ChannelFuture connect();

    /**
     * @return {@link io.netty.channel.ChannelFuture} returned when closing a channel to remote server.
     */
    public ChannelFuture disconnect();

    /**
     * @return {@link io.netty.channel.ChannelFuture} returned when reopening a channel to remote server.
     */
    public ChannelFuture reconnect();

    /**
     * @return <code>true</code> if channel to remote server is open
     */
    public boolean isConnected();

    public Message sendMessage(Message message);

    public ProtocolBootstrap getProtocolBootstrap();

    public ServerDetail getServerDetail();

    public MessageExchange getMessageExchange();
}

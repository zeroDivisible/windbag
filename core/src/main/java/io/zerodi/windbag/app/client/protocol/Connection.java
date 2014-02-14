package io.zerodi.windbag.app.client.protocol;

import io.netty.channel.ChannelFuture;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.client.registry.ProtocolBootstrap;

/**
 * Interface abstracting connection to a remote server.
 *
 * @author zerodi
 */
public interface Connection {

    public ChannelFuture connect();

    public boolean isConnected();

    public ChannelFuture disconnect();

    public ChannelFuture sendMessage(Message message);

    public ProtocolBootstrap getProtocolBootstrap();

    public ServerDetail getServerDetail();
}

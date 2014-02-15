package io.zerodi.windbag.app.client.protocol;

import io.netty.buffer.ByteBuf;

/**
 * Marker interface for messages received from specific TCP protocols.
 * @author zerodi
 */
public interface Message {

    public String getResponse();

    public ByteBuf getByteBuf();

    public void setResponse(String response);
}

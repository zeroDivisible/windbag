package io.zerodi.windbag.app.protocol;

import io.netty.buffer.ByteBuf;

/**
 * Marker interface for messages received from specific TCP protocols.
 * @author zerodi
 */
public interface Message {

    public String getResponse();

    public ByteBuf asByteBuf();

    public void setResponse(String response);
}

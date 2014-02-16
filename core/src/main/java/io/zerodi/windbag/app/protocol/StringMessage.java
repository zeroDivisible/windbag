package io.zerodi.windbag.app.protocol;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class StringMessage implements Message {
    private static final Logger logger = LoggerFactory.getLogger(StringMessage.class);

    private final String message;
    private String response;

    private StringMessage(byte[] message) {
        this.message = new String(message, CharsetUtil.UTF_8);
        logger.debug("new EPP Message: {}", message);
    }

    public StringMessage(String message) {
        this.message = message;
        logger.debug("new EPP Message: {}", message);
    }

    public static Message getInstance(byte[] message) {
        return new StringMessage(message);
    }

    public static Message getInstance(String message) {
        return new StringMessage(message);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "StringMessage{" + "message='" + message + '\'' + '}';
    }

    @Override
    public String getResponse() {
        return response;
    }

    @Override
    public ByteBuf asByteBuf() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(message.getBytes(CharsetUtil.UTF_8));

        return byteBuf;
    }

    @Override
    public void setResponse(String response) {
        this.response = response;

    }
}

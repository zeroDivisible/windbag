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
    private final MessageType messageType;

    private StringMessage(byte[] message, MessageType messageType) {
        this.messageType = messageType;
        this.message = new String(message, CharsetUtil.UTF_8);
    }

    public StringMessage(String message, MessageType messageType) {
        this.message = message;
        this.messageType = messageType;
    }

    public static Message getInstance(byte[] message, MessageType messageType) {
        return new StringMessage(message, messageType);
    }

    public static Message getInstance(String message, MessageType messageType) {
        return new StringMessage(message, messageType);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public MessageType getType() {
        return messageType;
    }

    @Override
    public String toString() {
        return "StringMessage{" +
                "message='" + message + '\'' +
                ", messageType=" + messageType +
                '}';
    }

    @Override
    public ByteBuf asByteBuf() {
        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(message.getBytes(CharsetUtil.UTF_8));

        return byteBuf;
    }
}

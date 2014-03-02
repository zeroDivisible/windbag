package io.zerodi.windbag.core.protocol;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.util.CharsetUtil;

/**
 * @author zerodi
 */
@JsonRootName("result")
public class StringMessage implements Message {

	@JsonProperty("raw_message")
	private final String message;

	@JsonProperty("message_type")
	private final MessageType messageType;

	private StringMessage(byte[] message, MessageType messageType) {
		this.messageType = messageType;
		this.message = new String(message, CharsetUtil.UTF_8);
	}

	private StringMessage(String message, MessageType messageType) {
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
	@JsonIgnore
	public MessageType getType() {
		return messageType;
	}

	@Override
	public ByteBuf asByteBuf() {
		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeBytes(message.getBytes(CharsetUtil.UTF_8));

		return byteBuf;
	}
}

package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.zerodi.windbag.app.protocol.Message;
import io.zerodi.windbag.app.protocol.MessageType;

import java.util.ArrayList;
import java.util.List;

/**
 * This is the result of posting a message to a connection.
 *
 * @author zerodi
 */
@JsonRootName("messages")
public class MessageList {

	private List<Message> messages = new ArrayList<>();

	private MessageList(List<Message> messages) {
		this.messages = messages;
	}

	public static MessageList getInstance(List<Message> messages) {
		return new MessageList(messages);
	}

	@JsonValue
	public List<Message> getMessages() {
		return messages;
	}
}

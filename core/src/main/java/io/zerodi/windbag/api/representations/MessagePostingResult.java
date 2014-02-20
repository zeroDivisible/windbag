package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.zerodi.windbag.app.protocol.Message;
import io.zerodi.windbag.app.protocol.MessageType;

/**
 * This is the result of posting a message to a connection.
 *
 * @author zerodi
 */
@JsonRootName("result")
public class MessagePostingResult {

	@JsonProperty("raw_response")
	private String rawResponse;

	@JsonProperty("message_type")
	private MessageType messageType;

	private MessagePostingResult(Message message) {
		this.rawResponse = message.getMessage();
		this.messageType = message.getType();
	}

	public static MessagePostingResult getInstance(Message message) {
		return new MessagePostingResult(message);
	}
}

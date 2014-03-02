package io.zerodi.windbag.core.protocol;

import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * @author zerodi
 */
public class ConnectionEstablishedMessage extends StringMessage {

	@JsonProperty("channel_id")
	private final String id;

	private ConnectionEstablishedMessage(String originalResponse, String connectionChannelId) {
		super(originalResponse, MessageType.SYSTEM_CONNECTION_ESTABLISHED);
		id = connectionChannelId;
	}

	public static ConnectionEstablishedMessage getInstance(String originalResponse, String connectionChannelId) {
		return new ConnectionEstablishedMessage(originalResponse, connectionChannelId);
	}

	public final String getId() {
		return id;
	}
}

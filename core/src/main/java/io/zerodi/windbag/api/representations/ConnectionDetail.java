package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.zerodi.windbag.core.protocol.Connection;

/**
 * @author zerodi
 */
@JsonRootName("connection")
public class ConnectionDetail {

	@JsonProperty
	private final long id;

	private ConnectionDetail(Connection connection) {
		id = connection.getId();
	}

	public static ConnectionDetail getInstance(Connection connection) {
		return new ConnectionDetail(connection);
	}
}

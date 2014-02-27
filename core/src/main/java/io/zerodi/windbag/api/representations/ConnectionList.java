package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.zerodi.windbag.core.protocol.Connection;

import java.util.List;

/**
 * Class which abstracts the results of returning the list of details of multiple connections to one server.
 * @author zerodi
 */
@JsonRootName("connections")
public class ConnectionList {

	private final List<Connection> connections;

	private ConnectionList(List<Connection> connections) {
		this.connections = connections;
	}

	public static ConnectionList getInstance(List<Connection> connections) {
	    return new ConnectionList(connections);
	}

	@JsonValue
	public List<Connection> getConnections() {
		return connections;
	}
}

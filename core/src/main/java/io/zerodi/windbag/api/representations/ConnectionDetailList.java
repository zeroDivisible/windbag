package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import io.zerodi.windbag.core.protocol.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which abstracts the results of returning the list of details of multiple connections to one server.
 *
 * @author zerodi
 */
@JsonRootName("connections")
public class ConnectionDetailList {

	private final List<ConnectionDetail> connections = new ArrayList<>();

	private ConnectionDetailList(List<Connection> connections) {
		for (Connection connection : connections) {
			this.connections.add(ConnectionDetail.getInstance(connection));
		}
	}

	public static ConnectionDetailList getInstance(List<Connection> connections) {
		return new ConnectionDetailList(connections);
	}

	@JsonValue
	public List<ConnectionDetail> getConnections() {
		return connections;
	}
}

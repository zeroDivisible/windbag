package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.Connection;

import java.util.ArrayList;
import java.util.List;

/**
 * Class which abstracts the results of returning the list of details of multiple connections to one server.
 *
 * @author zerodi
 */
@JsonRootName("server")
public class ServerDetailRepresentation {

	@JsonProperty("id")
	private String id;

	@JsonProperty("server_address")
	private String serverAddress;

	@JsonProperty("server_port")
	private int serverPort;

	@JsonProperty("protocol")
	private Protocol protocol;

	@JsonProperty("active_connections")
	private final List<ConnectionDetail> connections = new ArrayList<>();


	private ServerDetailRepresentation(ServerDetail serverDetail, List<Connection> connections) {
		this.id = serverDetail.getId();
		this.serverAddress = serverDetail.getServerAddress();
		this.serverPort = serverDetail.getServerPort();
		this.protocol = serverDetail.getProtocol();

		for (Connection connection : connections) {
			this.connections.add(ConnectionDetail.getInstance(connection));
		}
	}

	public static ServerDetailRepresentation getInstance(ServerDetail serverDetail, List<Connection> connections) {
		return new ServerDetailRepresentation(serverDetail, connections);
	}
}

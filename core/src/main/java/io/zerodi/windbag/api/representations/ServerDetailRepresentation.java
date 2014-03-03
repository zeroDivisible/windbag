package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.yammer.dropwizard.json.JsonSnakeCase;
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
@JsonSnakeCase
public class ServerDetailRepresentation {

	@JsonProperty
	private String id;

	@JsonProperty
	private String serverAddress;

	@JsonProperty
	private int serverPort;

	@JsonProperty
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

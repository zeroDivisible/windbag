package io.zerodi.windbag.api.resources;

import com.yammer.metrics.annotation.Timed;
import io.zerodi.windbag.api.ApiSettings;
import io.zerodi.windbag.api.representations.MessageList;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.api.representations.ServerDetailRepresentation;
import io.zerodi.windbag.app.registry.ConnectionRegistryImpl;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.List;

import static javax.ws.rs.core.Response.Status;

/**
 * Resource which is controlling servers defined in this application
 *
 * @author zerodi
 */
@Path(ApiSettings.API_URL_PREFIX + "/server")
@Produces(MediaType.APPLICATION_JSON)
public class ServerControlResource {

	private static final Logger logger = LoggerFactory.getLogger(ServerControlResource.class);

	private final ApplicationConfiguration configuration;
	private final ConnectionRegistryImpl   connectionRegistry;

	private ServerControlResource(ApplicationConfiguration configuration, ConnectionRegistryImpl connectionRegistry) {
		this.configuration = configuration;
		this.connectionRegistry = connectionRegistry;
	}

	public static ServerControlResource getInstance(ApplicationConfiguration configuration, ConnectionRegistryImpl connectionRegistry) {
		return new ServerControlResource(configuration, connectionRegistry);
	}

	@GET
	@Path("{serverId}/connect")
	@Timed
	public Message connectToServer(@PathParam("serverId") String serverId) throws
	                                                                       InterruptedException {
		ServerDetail server = findServer(serverId);
		Connection connection = connectionRegistry.createAndRegisterConnection(server);

		// connects and waits till the connection is successful.
		Message connectionResult = connection.getHandler().connect();
		logger.debug("connection successful for {}", serverId);

		return ConnectionEstablishedMessage.getInstance(connectionResult.getMessage(), "" + connection.getId());
	}

	/**
	 * @param serverId which we would like to retrieve from the registry
	 * @return found {@link io.zerodi.windbag.core.protocol.Connection}
	 * @throws javax.ws.rs.WebApplicationException with status of {@link javax.ws.rs.core.Response.Status#NOT_FOUND NOT_FOUND} if none
	 *                                             matches the name.
	 */
	private ServerDetail findServer(String serverId) {
		for (ServerDetail serverDetail : configuration.getServers()) {
			if (serverDetail.getName()
			                .equals(serverId)) {
				return serverDetail;
			}
		}

		throw new WebApplicationException(Status.NOT_FOUND);
	}

	@GET
	@Path("{serverId}")
	@Timed
	public ServerDetailRepresentation getServerDetails(@PathParam("serverId") String serverId) {
		ServerDetail server = findServer(serverId);
		List<Connection> connections = connectionRegistry.getAllForServer(serverId);
		return ServerDetailRepresentation.getInstance(server, connections);
	}

	@GET
	@Path("{serverId}/{connectionId}/disconnect")
	@Timed
	public Message disconnectFromServer(@PathParam("serverId") String serverId,
	                                    @PathParam("connectionId") Long connectionId) {
		Connection connection = connectionRegistry.getForServerWithId(serverId,
		                                                              connectionId);
		if (connection == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		return connectionRegistry.disconnectAndDeregister(connection);
	}

	@GET
	@Path("{serverId}/{connectionId}/send/{message}")
	@Timed
	public Message sendMessage(@PathParam("serverId") String serverId,
	                           @PathParam("connectionId") Long connectionId,
	                           @PathParam("message") final String message) throws InterruptedException {
		Connection connection = connectionRegistry.getForServerWithId(serverId, connectionId);
		if (connection == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		Message messageToSend = StringMessage.getInstance(message, MessageType.OUTBOUND);
		return connection.getHandler().sendMessage(messageToSend);
	}

	@POST
	@Path("{serverId}/{connectionId}/send")
	public Message postMessage(@PathParam("serverId") String serverId,
	                           @PathParam("connectionId") Long connectionId,
	                           String message) {
		Connection connection = connectionRegistry.getForServerWithId(serverId, connectionId);
		if (connection == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		return connection.getHandler().sendMessage(StringMessage.getInstance(message, MessageType.OUTBOUND));
	}


	@GET
	@Path("{serverId}/{connectionId}/messages")
	@Timed
	public MessageList getMessages(@PathParam("serverId") String serverId,
	                               @PathParam("connectionId") Long connectionId) {
		Connection connection = connectionRegistry.getForServerWithId(serverId, connectionId);
		if (connection == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		return MessageList.getInstance(connection.getMessageExchange().getLast(20));
	}
}

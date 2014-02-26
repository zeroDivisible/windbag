package io.zerodi.windbag.api.resources;

import com.yammer.metrics.annotation.Timed;
import io.zerodi.windbag.api.ApiSettings;
import io.zerodi.windbag.api.representations.MessageList;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.Message;
import io.zerodi.windbag.core.protocol.MessageType;
import io.zerodi.windbag.core.protocol.StringMessage;
import io.zerodi.windbag.app.registry.ConnectionRegistryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

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

	private final ConnectionRegistryImpl channelRegistry;

	private ServerControlResource(ConnectionRegistryImpl channelRegistry) {
		this.channelRegistry = channelRegistry;
	}

	public static ServerControlResource getInstance(ConnectionRegistryImpl channelRegistry) {
		return new ServerControlResource(channelRegistry);
	}

	@GET
	@Path("{serverId}/connect")
	@Timed
	public Message connectToServer(@PathParam("serverId") String serverId) throws InterruptedException {
		Connection connection = findServer(serverId);

		// connects and waits till the connection is successful.
		Message connectionResult = connection.connect();
		logger.debug("connection successful for {}", serverId);
		return connectionResult;
	}

	@GET
	@Path("{serverId}/disconnect")
	@Timed
	public Message disconnectFromServer(@PathParam("serverId") String serverId) {
		Connection connection = findServer(serverId);
		return connection.disconnect();
	}

	@GET
	@Path("{serverId}/send/{message}")
	@Timed
	public Message sendMessage(@PathParam("serverId") String serverId, @PathParam("message") final String message)
			throws InterruptedException {
		Connection connection = channelRegistry.getConnection(serverId);
		if (connection == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		Message messageToSend = StringMessage.getInstance(message, MessageType.OUTBOUND);
		return connection.sendMessage(messageToSend);
	}

	@POST
	@Path("{serverId}/send")
	public Message postMessage(@PathParam("serverId") String serverId, String message) {
		Connection connection = channelRegistry.getConnection(serverId);
		if (connection == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		return connection.sendMessage(StringMessage.getInstance(message, MessageType.OUTBOUND));
	}

	@GET
	@Path("{serverId}/messages")
	@Timed
	public MessageList getMessages(@PathParam("serverId") String serverId) {
		Connection connection = channelRegistry.getConnection(serverId);
		if (connection == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		return MessageList.getInstance(connection.getMessageExchange().getLast(10));
	}

	/**
	 * @param serverId which we would like to retrieve from the registry
	 * @return found {@link io.zerodi.windbag.core.protocol.Connection}
	 * @throws javax.ws.rs.WebApplicationException with status of {@link javax.ws.rs.core.Response.Status#NOT_FOUND NOT_FOUND} if none matches the name.
	 */
	private Connection findServer(String serverId) {
		Connection connection = channelRegistry.getConnection(serverId);
		if (connection == null) {
			throw new WebApplicationException(Status.NOT_FOUND);
		}

		return connection;
	}
}

package io.zerodi.windbag.api.resources;

import com.yammer.metrics.annotation.Timed;
import io.netty.channel.ChannelFuture;
import io.zerodi.windbag.api.ApiSettings;
import io.zerodi.windbag.api.representations.MessageList;
import io.zerodi.windbag.app.protocol.Connection;
import io.zerodi.windbag.app.protocol.Message;
import io.zerodi.windbag.app.protocol.MessageType;
import io.zerodi.windbag.app.protocol.StringMessage;
import io.zerodi.windbag.app.registry.ChannelRegistryImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.concurrent.TimeUnit;

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

	private final ChannelRegistryImpl channelRegistry;

	private ServerControlResource(ChannelRegistryImpl channelRegistry) {
		this.channelRegistry = channelRegistry;
	}

	public static ServerControlResource getInstance(ChannelRegistryImpl channelRegistry) {
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
		Message response = connection.sendMessage(messageToSend);

		return response;
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
	 * @return found {@link io.zerodi.windbag.app.protocol.Connection}
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

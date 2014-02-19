package io.zerodi.windbag.api.resources;

import static javax.ws.rs.core.Response.Status;

import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

import io.zerodi.windbag.app.protocol.MessageType;
import io.zerodi.windbag.app.protocol.StringMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.metrics.annotation.Timed;

import io.netty.channel.ChannelFuture;
import io.zerodi.windbag.api.ApiSettings;
import io.zerodi.windbag.app.protocol.Connection;
import io.zerodi.windbag.app.protocol.Message;
import io.zerodi.windbag.app.registry.ChannelRegistryImpl;

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
    public String connectToServer(@PathParam("serverId") String serverId) throws InterruptedException {
        Connection connection = findServer(serverId);

        // connects and waits till the connection is successful.
        ChannelFuture connectionFuture = connection.connect();
        if (connectionFuture != null) {
            boolean connected = connectionFuture.awaitUninterruptibly(10, TimeUnit.SECONDS);

            if (connected) {
                logger.debug("connection successful for {}", serverId);
            } else {
                throw new WebApplicationException(Status.INTERNAL_SERVER_ERROR);
            }

        }
        return "" + connection.getMessageExchange();
    }

    @GET
    @Path("{serverId}/disconnect")
    @Timed
    public String disconnectFromServer(@PathParam("serverId") String serverId) {
        Connection connection = findServer(serverId);
        if (connection.isConnected()) {
            connection.disconnect();
        }
        return "" + connection.getMessageExchange();
    }

    @GET
    @Path("{serverId}/send/{message}")
    @Timed
    public String sendMessage(@PathParam("serverId") String serverId, @PathParam("message") final String message)
            throws InterruptedException {
        Connection connection = channelRegistry.getConnection(serverId);
        if (connection == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        final Message messageToSend = StringMessage.getInstance(message, MessageType.OUTBOUND);
        Message response = connection.sendMessage(messageToSend);

        return "" + response;
    }

    @GET
    @Path("{serverId}/messages")
    @Timed
    public String getMessages(@PathParam("serverId") String serverId) {
        Connection connection = channelRegistry.getConnection(serverId);
        if (connection == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        return "" + connection.getMessageExchange();
    }

    /**
     * @param serverId
     *            which we would like to retrieve from the registry
     * @return found {@link io.zerodi.windbag.app.protocol.Connection}
     *
     * @throws javax.ws.rs.WebApplicationException
     *             with status of {@link javax.ws.rs.core.Response.Status#NOT_FOUND NOT_FOUND} if none matches the name.
     */
    private Connection findServer(String serverId) {
        Connection connection = channelRegistry.getConnection(serverId);
        if (connection == null) {
            throw new WebApplicationException(Status.NOT_FOUND);
        }

        return connection;
    }
}

package io.zerodi.windbag.api.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.metrics.annotation.Timed;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.zerodi.windbag.api.ApiSettings;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.client.registry.ChannelRegistryImpl;
import io.zerodi.windbag.app.client.registry.ClientConnection;
import io.zerodi.windbag.app.client.registry.ProtocolBootstrap;

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
        ClientConnection clientConnection = channelRegistry.getClientConnection(serverId);
        if (clientConnection == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        ChannelFuture connect = clientConnection.connect().sync();

        // TODO find way of closing a channel
        // future.channel().closeFuture().sync();

        return "connecting to " + serverId;
    }

    @GET
    @Path("{serverId}/disconnect")
    @Timed
    public String disconnectFromServer(@PathParam("serverId") String serverId) {
        return "disconnecting from " + serverId;
    }

    @GET
    @Path("{serverId}/send/{message}")
    @Timed
    public String sendMessage(@PathParam("serverId") String serverId, @PathParam("message") String message) {
        return "will send '" + message + "' to " + serverId;
    }

}

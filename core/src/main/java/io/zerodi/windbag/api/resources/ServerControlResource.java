package io.zerodi.windbag.api.resources;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.yammer.metrics.annotation.Timed;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.util.CharsetUtil;
import io.zerodi.windbag.api.ApiSettings;
import io.zerodi.windbag.app.client.protocol.Connection;
import io.zerodi.windbag.app.client.registry.ChannelRegistryImpl;

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
        Connection connection = channelRegistry.getConnection(serverId);
        if (connection == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        ChannelFuture connectFuture = connection.connect().sync();
        return "done.";
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
    public String sendMessage(@PathParam("serverId") String serverId, @PathParam("message") String message) throws InterruptedException {
        Connection connection = channelRegistry.getConnection(serverId);
        if (connection == null) {
            throw new WebApplicationException(Response.Status.NOT_FOUND);
        }

        connection.sendMessage(null).sync();
        return "done.";
    }

}

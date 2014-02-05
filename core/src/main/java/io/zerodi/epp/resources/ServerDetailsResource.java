package io.zerodi.epp.resources;

import com.yammer.metrics.annotation.Timed;
import io.zerodi.epp.ServerDetail;
import io.zerodi.epp.UrlConstants;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author zerodi
 */
@Path(UrlConstants.API_URL_PREFIX + "/configuration/servers")
@Produces(MediaType.APPLICATION_JSON)
public class ServerDetailsResource {

    private final List<ServerDetail> serverDetails;

    private ServerDetailsResource(List<ServerDetail> serverDetails) {
        this.serverDetails = serverDetails;
    }

    public static ServerDetailsResource getInstance(List<ServerDetail> serverDetails) {
        return new ServerDetailsResource(serverDetails);
    }

    @GET
    @Timed
    public ServerDetailsList getDetails() {
        return ServerDetailsList.getInstance(serverDetails);
    }
}

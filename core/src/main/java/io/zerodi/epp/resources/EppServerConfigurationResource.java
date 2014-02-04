package io.zerodi.epp.resources;

import com.yammer.metrics.annotation.Timed;
import io.zerodi.epp.UrlConstants;
import io.zerodi.epp.core.EppServerDetails;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * @author zerodi
 */
@Path(UrlConstants.API_URL_PREFIX + "/epp-server")
@Produces(MediaType.APPLICATION_JSON)
public class EppServerConfigurationResource {

    private final String serverAddress;
    private final int serverPort;

    private EppServerConfigurationResource(String serverAddress, int serverPort) {
        this.serverAddress = serverAddress;
        this.serverPort = serverPort;
    }

    public static EppServerConfigurationResource getInstance(String serverAddress, int serverPort) {
        return new EppServerConfigurationResource(serverAddress, serverPort);
    }


    @GET
    @Timed
    public EppServerDetails getDetails() {
        return EppServerDetails.getInstance(serverAddress, serverPort);
    }
}

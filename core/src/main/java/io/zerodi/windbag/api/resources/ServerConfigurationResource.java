package io.zerodi.windbag.api.resources;

import com.yammer.metrics.annotation.Timed;
import io.zerodi.windbag.api.ApiSettings;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.api.representations.ServerDetailsList;
import io.zerodi.windbag.core.ApplicationConfiguration;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

/**
 * @author zerodi
 */
@Path(ApiSettings.API_URL_PREFIX + "/configuration/servers")
@Produces(MediaType.APPLICATION_JSON)
public class ServerConfigurationResource {

	private final ApplicationConfiguration configuration;

	private ServerConfigurationResource(ApplicationConfiguration configuration) {
		this.configuration = configuration;
	}

	public static ServerConfigurationResource getInstance(ApplicationConfiguration configuration) {
		return new ServerConfigurationResource(configuration);
	}

	@GET
	@Timed
	public ServerDetailsList getDetails() {
		return ServerDetailsList.getInstance(configuration.getServers());
	}
}

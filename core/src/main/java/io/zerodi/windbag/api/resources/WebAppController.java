package io.zerodi.windbag.api.resources;

import com.yammer.metrics.annotation.Timed;
import io.zerodi.windbag.api.representations.webapp.MainView;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

/**
 * This is the controller which is serving the UI layer for this application.
 *
 * @author zerodi
 */
@Path("/")
@Produces(MediaType.TEXT_HTML)
public class WebAppController {

	private WebAppController() {
	}

	public static WebAppController getInstance() {
	    return new WebAppController();
	}

	@GET
	@Timed
	public MainView getHomePage() {
		return MainView.getInstance();

	}
}

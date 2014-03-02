package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * Class which abstracts returning the details of {@link ServerDetail}
 *
 * @author zerodi
 */
@JsonRootName("servers")
public class ServerDetailRepresentationList {

	private List<ServerDetailRepresentation> serverDetails;

	private ServerDetailRepresentationList(List<ServerDetailRepresentation> serverDetails) {
		this.serverDetails = serverDetails;
	}

	public static ServerDetailRepresentationList getInstance(List<ServerDetailRepresentation> serverDetails) {
		return new ServerDetailRepresentationList(serverDetails);
	}

	@JsonValue
	public List<ServerDetailRepresentation> getServerDetails() {
		return serverDetails;
	}
}

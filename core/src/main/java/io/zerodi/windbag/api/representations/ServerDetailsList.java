package io.zerodi.windbag.api.representations;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.List;

/**
 * Class which abstracts returning the details of {@link ServerDetail}
 * @author zerodi
 */
@JsonRootName("server_details")
public class ServerDetailsList {

    private List<ServerDetail> serverDetails;

    private ServerDetailsList(List<ServerDetail> serverDetails) {
        this.serverDetails = serverDetails;
    }

    public static ServerDetailsList getInstance(List<ServerDetail> serverDetails) {
        return new ServerDetailsList(serverDetails);
    }

    @JsonValue
    public List<ServerDetail> getServerDetails() {
        return serverDetails;
    }
}

package io.zerodi.epp.resources;

import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.annotation.JsonValue;
import com.yammer.dropwizard.json.JsonSnakeCase;
import io.zerodi.epp.ServerDetail;

import java.util.List;

/**
 * Class which abstracts returning the details of {@link io.zerodi.epp.ServerDetail}
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

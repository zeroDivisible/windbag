package io.zerodi.epp;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zerodi
 */
public class ServerDetail {

    @NotEmpty
    @JsonProperty
    private String name;

    @NotEmpty
    @JsonProperty
    private String serverAddress;

    @NotEmpty
    @JsonProperty
    private String serverPort;

    public String getName() {
        return name;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public String getServerPort() {
        return serverPort;
    }
}

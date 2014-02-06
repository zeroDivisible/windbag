package io.zerodi.windbag.api.representations;

import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

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

    public void setName(String name) {
        this.name = name;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setServerPort(String serverPort) {
        this.serverPort = serverPort;
    }
}

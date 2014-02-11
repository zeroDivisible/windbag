package io.zerodi.windbag.api.representations;

import io.zerodi.windbag.core.Protocol;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonProperty;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;

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
    @Min(1)
    @Max(65535)
    private int serverPort;

    @NotEmpty
    @JsonProperty
    private Protocol protocol;

    public String getName() {
        return name;
    }

    public String getServerAddress() {
        return serverAddress;
    }

    public int getServerPort() {
        return serverPort;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setServerAddress(String serverAddress) {
        this.serverAddress = serverAddress;
    }

    public void setServerPort(int serverPort) {
        this.serverPort = serverPort;
    }

    public Protocol getProtocol() {
        return protocol;
    }

    public void setProtocol(Protocol protocol) {
        this.protocol = protocol;
    }
}

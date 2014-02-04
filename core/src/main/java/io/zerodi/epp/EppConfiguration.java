package io.zerodi.epp;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * @author zerodi
 */
public class EppConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private String eppServerAddress;

    @JsonProperty
    private int eppServerPort;

    public String getEppServerAddress() {
        return eppServerAddress;
    }

    public int getEppServerPort() {
        return eppServerPort;
    }

}

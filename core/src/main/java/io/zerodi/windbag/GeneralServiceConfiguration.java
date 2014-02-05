package io.zerodi.windbag;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author zerodi
 */
public class GeneralServiceConfiguration extends Configuration {

    @NotEmpty
    @JsonProperty
    private List<ServerDetail> servers;


    public List<ServerDetail> getServers() {
        return servers;
    }
}

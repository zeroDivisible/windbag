package io.zerodi.windbag.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import io.zerodi.windbag.api.representations.ServerDetail;
import org.hibernate.validator.constraints.NotEmpty;

import java.util.List;

/**
 * @author zerodi
 */
public class ApplicationConfiguration extends Configuration {

	@NotEmpty
	@JsonProperty
	private List<ServerDetail> servers;

	public List<ServerDetail> getServers() {
		return servers;
	}
}

package io.zerodi.windbag.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import io.zerodi.windbag.api.representations.ServerDetail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author zerodi
 */
public class ApplicationConfiguration extends Configuration {

	@JsonProperty("connection_timeout_seconds")
	@Min(0)
	private int connectionTimeoutSeconds;

	@JsonProperty("response_timeout_seconds")
	@Min(0)
	private int responseTimeoutSeconds;

	@NotEmpty
	@JsonProperty
	private List<ServerDetail> servers;

	public List<ServerDetail> getServers() {
		return servers;
	}

	public int getConnectionTimeoutSeconds() {
		return connectionTimeoutSeconds;
	}

	public int getResponseTimeoutSeconds() {
		return responseTimeoutSeconds;
	}
}

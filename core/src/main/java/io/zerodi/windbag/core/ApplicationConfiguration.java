package io.zerodi.windbag.core;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.yammer.dropwizard.config.Configuration;
import com.yammer.dropwizard.json.JsonSnakeCase;
import io.zerodi.windbag.api.representations.ServerDetail;
import org.hibernate.validator.constraints.NotEmpty;

import javax.validation.constraints.Min;
import java.util.List;

/**
 * @author zerodi
 */
@JsonSnakeCase
public class ApplicationConfiguration extends Configuration {

	@JsonProperty
	@Min(0)
	private int connectionTimeoutSeconds;

	@JsonProperty
	@Min(0)
	private int responseTimeoutSeconds;

	@JsonProperty
	@Min(0)
	private int defaultMessagesReturned;

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

	public int getDefaultMessagesReturned() {
		return defaultMessagesReturned;
	}

	public void setDefaultMessagesReturned(int defaultMessagesReturned) {
		this.defaultMessagesReturned = defaultMessagesReturned;
	}
}

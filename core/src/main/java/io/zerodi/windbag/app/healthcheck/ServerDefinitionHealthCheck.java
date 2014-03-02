package io.zerodi.windbag.app.healthcheck;

import com.yammer.metrics.core.HealthCheck;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;

import java.util.List;

/**
 * Basic healthcheck, which is rather proving the point then really checking anything.
 *
 * @author zerodi
 */
public class ServerDefinitionHealthCheck extends HealthCheck {

	private final ApplicationConfiguration configuration;

	private ServerDefinitionHealthCheck(ApplicationConfiguration configuration) {
		super("server-definitions");
		this.configuration = configuration;
	}

	public static ServerDefinitionHealthCheck getInstance(ApplicationConfiguration configuration) {
		return new ServerDefinitionHealthCheck(configuration);
	}

	@Override
	protected Result check() throws
	                         Exception {
		List<ServerDetail> servers = configuration.getServers();
		if (servers == null || servers.isEmpty()) {
			return Result.unhealthy("no server definitions found");
		}

		return Result.healthy();
	}
}

package io.zerodi.windbag.app.healthcheck;

import com.yammer.metrics.core.HealthCheck;
import io.zerodi.windbag.api.representations.ServerDetail;

import java.util.List;

/**
 * Basic healthcheck, which is rather proving the point then really checking anything.
 *
 * @author zerodi
 */
public class ServerDefinitionHealthCheck extends HealthCheck {

    private final List<ServerDetail> servers;

    private ServerDefinitionHealthCheck(List<ServerDetail> servers) {
        super("server-definitions");
        this.servers = servers;
    }

    public static ServerDefinitionHealthCheck getInstance(List<ServerDetail> servers) {
        return new ServerDefinitionHealthCheck(servers);
    }

    @Override
    protected Result check() throws Exception {
        if (servers == null || servers.isEmpty()) {
            return Result.unhealthy("no server definitions found");
        }

        return Result.healthy();
    }
}

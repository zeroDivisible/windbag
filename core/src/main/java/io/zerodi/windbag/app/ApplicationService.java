package io.zerodi.windbag.app;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import io.zerodi.windbag.app.healthchecks.ServerDefinitionHealthCheck;
import io.zerodi.windbag.api.resources.ServerDetailsResource;
import io.zerodi.windbag.app.client.netty.TestTcpClient;

/**
 * Main class, spinning the core application.
 *
 * @author zerodi
 */
public class ApplicationService extends Service<ApplicationConfiguration> {

    public static void main(String[] args) throws Exception {
        new ApplicationService().run(args);
    }

    @Override
    public void initialize(Bootstrap<ApplicationConfiguration> bootstrap) {
        bootstrap.setName("windbag");
        bootstrap.getObjectMapperFactory().enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Override
    public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
        environment.manage(TestTcpClient.getInstance());

        environment.addResource(ServerDetailsResource.getInstance(configuration.getServers()));
        environment.addHealthCheck(ServerDefinitionHealthCheck.getInstance(configuration.getServers()));
    }
}

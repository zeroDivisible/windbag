package io.zerodi.windbag.app;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.api.resources.ServerConfigurationResource;
import io.zerodi.windbag.api.resources.ServerControlResource;
import io.zerodi.windbag.app.client.registery.ChannelRegistryImpl;
import io.zerodi.windbag.app.healthcheck.ServerDefinitionHealthCheck;

import java.util.List;

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
        List<ServerDetail> defaultServers = configuration.getServers();
        environment.addResource(ServerConfigurationResource.getInstance(defaultServers));
        environment.addResource(ServerControlResource.getInstance());

        ChannelRegistryImpl channelRegistryImpl = ChannelRegistryImpl.getInstance();
        environment.manage(channelRegistryImpl);

        environment.addHealthCheck(ServerDefinitionHealthCheck.getInstance(defaultServers));
    }
}

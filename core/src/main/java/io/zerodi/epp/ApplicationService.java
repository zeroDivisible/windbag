package io.zerodi.epp;


import com.fasterxml.jackson.databind.SerializationFeature;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import io.zerodi.epp.healtchecks.ServerDefinitionHealthCheck;
import io.zerodi.epp.resources.ServerDetailsResource;

/**
 * Main class, spinning the core application.
 * @author zerodi
 */
public class ApplicationService extends Service<GeneralServiceConfiguration> {

    public static void main(String[] args) throws Exception {
        new ApplicationService().run(args);
    }

    @Override
    public void initialize(Bootstrap<GeneralServiceConfiguration> bootstrap) {
        bootstrap.setName("server-wizard");

        bootstrap.getObjectMapperFactory().enable(SerializationFeature.WRAP_ROOT_VALUE);
    }

    @Override
    public void run(GeneralServiceConfiguration configuration, Environment environment) throws Exception {
        environment.addResource(ServerDetailsResource.getInstance(configuration.getServers()));
        environment.addHealthCheck(ServerDefinitionHealthCheck.getInstance(configuration.getServers()));
    }
}

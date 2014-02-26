package io.zerodi.windbag.core;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.api.resources.ServerConfigurationResource;
import io.zerodi.windbag.api.resources.ServerControlResource;
import io.zerodi.windbag.api.resources.WebAppController;
import io.zerodi.windbag.app.healthcheck.ServerDefinitionHealthCheck;
import io.zerodi.windbag.app.registry.ConnectionRegistry;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.ProtocolBootstrapFactoryRegistry;
import io.zerodi.windbag.app.registry.ConnectionRegistryImpl;

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

		bootstrap.addBundle(new AssetsBundle("/assets"));
		bootstrap.addBundle(new ViewBundle());
	}

	@Override
	public void run(ApplicationConfiguration configuration, Environment environment) throws Exception {
		List<ServerDetail> defaultServers = configuration.getServers();

		ProtocolBootstrapFactoryRegistry protocolBootstrapFactoryRegistry = ProtocolBootstrapFactoryRegistry.getInstance();
		ConnectionRegistryImpl connectionRegistry = ConnectionRegistryImpl.getInstance(protocolBootstrapFactoryRegistry);

		environment.manage(connectionRegistry); // registry of open connections will need to be shut down along with the main application.

		environment.addResource(ServerConfigurationResource.getInstance(configuration));
		environment.addResource(ServerControlResource.getInstance(configuration, connectionRegistry));
		environment.addResource(WebAppController.getInstance());

		environment.addHealthCheck(ServerDefinitionHealthCheck.getInstance(defaultServers));
	}
}

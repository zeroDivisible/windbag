package io.zerodi.windbag.core;

import com.fasterxml.jackson.databind.SerializationFeature;
import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.assets.AssetsBundle;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import com.yammer.dropwizard.views.ViewBundle;
import io.zerodi.windbag.api.resources.ServerControlResource;
import io.zerodi.windbag.api.resources.WebAppController;
import io.zerodi.windbag.app.healthcheck.ServerDefinitionHealthCheck;
import io.zerodi.windbag.app.registry.ConnectionRegistryImpl;
import io.zerodi.windbag.core.protocol.ConnectionFactoryRegistry;

/**
 * Main class, spinning the core application.
 *
 * @author zerodi
 */
class ApplicationService extends Service<ApplicationConfiguration> {

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
		ConnectionFactoryRegistry connectionFactoryRegistry = ConnectionFactoryRegistry.getInstance(configuration);
		ConnectionRegistryImpl connectionRegistry = ConnectionRegistryImpl.getInstance(connectionFactoryRegistry);

		environment.manage(connectionRegistry); // registry of open connections will need to be shut down along with the main application.

		environment.addResource(ServerControlResource.getInstance(configuration, connectionRegistry));
		environment.addResource(WebAppController.getInstance());

		environment.addHealthCheck(ServerDefinitionHealthCheck.getInstance(configuration));
	}
}

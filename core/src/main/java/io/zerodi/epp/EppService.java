package io.zerodi.epp;


import com.yammer.dropwizard.Service;
import com.yammer.dropwizard.config.Bootstrap;
import com.yammer.dropwizard.config.Environment;
import io.zerodi.epp.resources.EppServerConfigurationResource;

/**
 * @author zerodi
 */
public class EppService extends Service<EppConfiguration> {

    public static void main(String[] args) throws Exception {
        new EppService().run(args);
    }

    @Override
    public void initialize(Bootstrap<EppConfiguration> bootstrap) {
        bootstrap.setName("epp-wizard");
    }

    @Override
    public void run(EppConfiguration configuration, Environment environment) throws Exception {
        String eppServerAddress = configuration.getEppServerAddress();
        int eppServerPort = configuration.getEppServerPort();

        environment.addResource(EppServerConfigurationResource.getInstance(eppServerAddress, eppServerPort));
        //TODO Implement
    }
}

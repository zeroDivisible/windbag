package io.zerodi.windbag.app.registry;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.ConnectionFactoryRegistry;
import io.zerodi.windbag.core.protocol.Connection;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import java.util.List;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class ConnectionRegistryImplTest {

	private ConnectionRegistryImpl channelRegistryImpl;
	private ConnectionFactoryRegistry protocolBootstrapFactory;
	private ApplicationConfiguration  applicationConfiguration;

	@BeforeMethod
	public void setUp() throws
	                    Exception {
		protocolBootstrapFactory = ConnectionFactoryRegistry.getInstance(applicationConfiguration);
		channelRegistryImpl = ConnectionRegistryImpl.getInstance(protocolBootstrapFactory);
	}

	@Test
	public void itShouldBePossibleToRegisterABootstrap() {
		// given
		ServerDetail serverDetail = new ServerDetail();
		serverDetail.setId("serverId");
		serverDetail.setProtocol(Protocol.NOOP);

		Connection connection = protocolBootstrapFactory.createConnection(serverDetail);

		// when
		channelRegistryImpl.registerConnection(connection);
		List<Connection> connections = channelRegistryImpl.getAllForServer("serverId");

		// then
		assertThat(connection.getId()).isNotEqualTo(0);
		assertThat(connections).hasSize(1);
		assertThat(connections.get(0)).isEqualTo(connection);
	}
}

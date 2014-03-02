package io.zerodi.windbag.app.protocol;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.ConnectionFactoryRegistry;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.ProtocolHandler;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class ConnectionFactoryImplTest {

	private ConnectionFactoryRegistry bootstrapFactory;

	private ApplicationConfiguration applicationConfiguration;

	@BeforeMethod
	public void setUp() throws
	                    Exception {
		bootstrapFactory = ConnectionFactoryRegistry.getInstance(applicationConfiguration);
	}

	@Test
	public void itShouldAllowToCreateProtocolBootstrapFromServerDetail() {
		// given
		ServerDetail serverDetail = new ServerDetail();
		serverDetail.setProtocol(Protocol.NOOP);

		// when
		Connection connection = bootstrapFactory.createConnection(serverDetail);
		ProtocolHandler protocolHandler = connection.getProtocolHandler();

		// then
		assertThat(protocolHandler).isNotNull();
		assertThat(protocolHandler.getProtocol()).isEqualTo(Protocol.NOOP);
	}
}

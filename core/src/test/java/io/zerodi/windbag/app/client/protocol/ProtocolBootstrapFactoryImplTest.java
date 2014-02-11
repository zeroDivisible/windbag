package io.zerodi.windbag.app.client.protocol;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.client.registry.ClientConnection;
import io.zerodi.windbag.app.client.registry.ProtocolBootstrap;
import io.zerodi.windbag.core.Protocol;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class ProtocolBootstrapFactoryImplTest {

    private ProtocolBootstrapFactoryImpl bootstrapFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        bootstrapFactory = ProtocolBootstrapFactoryImpl.getInstance();
    }

    @Test
    public void itShouldAllowToCreateProtocolBootstrapFromServerDetail() {
        // given
        ServerDetail serverDetail = new ServerDetail();
        serverDetail.setProtocol(Protocol.NOOP);

        // when
        ClientConnection clientConnection = bootstrapFactory.createClientConnection(serverDetail);
        ProtocolBootstrap protocolBootstrap = clientConnection.getProtocolBootstrap();

        // then
        assertThat(protocolBootstrap).isNotNull();
        assertThat(protocolBootstrap.getProtocol()).isEqualTo(Protocol.NOOP);
    }
}

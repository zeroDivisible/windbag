package io.zerodi.windbag.app.protocol;

import static org.fest.assertions.Assertions.assertThat;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;
import io.zerodi.windbag.core.Protocol;

/**
 * @author zerodi
 */
public class ConnectionFactoryImplTest {

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
        Connection connection = bootstrapFactory.createConnection(serverDetail);
        ProtocolBootstrap protocolBootstrap = connection.getProtocolBootstrap();

        // then
        assertThat(protocolBootstrap).isNotNull();
        assertThat(protocolBootstrap.getProtocol()).isEqualTo(Protocol.NOOP);
    }
}

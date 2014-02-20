package io.zerodi.windbag.app.protocol.epp;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.protocol.Connection;
import io.zerodi.windbag.app.protocol.MessageExchange;
import io.zerodi.windbag.app.protocol.MessageExchangeImpl;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class EppConnectionTest {

    private MessageExchange messageExchange;

    private ServerDetail serverDetail;

    private ProtocolBootstrap protocolBootstrap;

    private Connection connection;

    @BeforeMethod
    public void setUp() throws Exception {
        messageExchange = MessageExchangeImpl.getInstance();
        serverDetail = new ServerDetail();
        protocolBootstrap = EppProtocolBootstrap.getInstance();

        connection = EppConnection.getInstance(serverDetail, protocolBootstrap);
    }

    @Test
    public void itShouldHaveMessageExchangeAssigned() {
        // given
        assertThat(connection.getMessageExchange()).isNotNull();
    }
}

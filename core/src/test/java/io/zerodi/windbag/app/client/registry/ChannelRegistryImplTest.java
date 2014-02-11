package io.zerodi.windbag.app.client.registry;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.client.protocol.ProtocolBootstrapFactoryImpl;
import io.zerodi.windbag.core.Protocol;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class ChannelRegistryImplTest {

    private ChannelRegistryImpl channelRegistryImpl;
    private ProtocolBootstrapFactoryImpl protocolBootstrapFactory;

    @BeforeMethod
    public void setUp() throws Exception {
        channelRegistryImpl = ChannelRegistryImpl.getInstance();
        protocolBootstrapFactory = ProtocolBootstrapFactoryImpl.getInstance();
    }

    @Test
    public void itShouldBePossibleToRegisterABootstrap() {
        // given
        ServerDetail serverDetail = new ServerDetail();
        serverDetail.setName("serverId");
        serverDetail.setProtocol(Protocol.NOOP);

        ClientConnection clientConnection = protocolBootstrapFactory.createClientConnection(serverDetail);

        // when
        channelRegistryImpl.registerClientConnection(clientConnection);
        ProtocolBootstrap returnedBootstrap = channelRegistryImpl.getClientConnection("serverId").getProtocolBootstrap();

        // then
        assertThat(returnedBootstrap).isEqualTo(clientConnection.getProtocolBootstrap());
    }
}

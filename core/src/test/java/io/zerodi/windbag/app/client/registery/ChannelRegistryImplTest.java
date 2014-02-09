package io.zerodi.windbag.app.client.registery;

import io.netty.bootstrap.Bootstrap;
import io.zerodi.windbag.app.client.protocol.noop.NoopProtocolBootstrap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class ChannelRegistryImplTest {

    private ChannelRegistryImpl channelRegistryImpl;

    @BeforeMethod
    public void setUp() throws Exception {
        channelRegistryImpl = ChannelRegistryImpl.getInstance();
    }

    @Test
    public void itShouldBePossibleToRegisterABootstrap() {
        // given
        String serverId = "serverId";
        ProtocolBootstrap bootstrap = NoopProtocolBootstrap.getInstance();

        // when
        channelRegistryImpl.registerChannel(serverId, bootstrap);
        ProtocolBootstrap returnedBootstrap = channelRegistryImpl.getChannelBootstrap(serverId);

        // then
        assertThat(returnedBootstrap).isEqualTo(bootstrap);
    }

}

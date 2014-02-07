package io.zerodi.windbag.app.client.netty;

import io.netty.bootstrap.Bootstrap;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class ServerChannelRegistryTest {

    private ServerChannelRegistry serverChannelRegistry;

    @BeforeMethod
    public void setUp() throws Exception {
        serverChannelRegistry = ServerChannelRegistry.getInstance();
    }

    @Test
    public void itShouldBePossibleToRegisterABootstrap() {
        // given
        String serverId = "serverId";
        Bootstrap bootstrap = new Bootstrap();

        // when
        serverChannelRegistry.registerChannel(serverId, bootstrap);
        Bootstrap returnedBootstrap = serverChannelRegistry.getChannelBootstrap(serverId);

        // then
        assertThat(returnedBootstrap).isEqualTo(bootstrap);
    }
}

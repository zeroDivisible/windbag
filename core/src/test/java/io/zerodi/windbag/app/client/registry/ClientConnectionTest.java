package io.zerodi.windbag.app.client.registry;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.util.CharsetUtil;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.app.client.protocol.epp.EppMessageReader;
import io.zerodi.windbag.app.client.protocol.epp.EppProtocolBootstrapFactory;
import io.zerodi.windbag.core.Protocol;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static org.fest.assertions.Assertions.assertThat;

/**
 * @author zerodi
 */
public class ClientConnectionTest {

    private ClientConnection clientConnection;

    @BeforeMethod
    public void setUp() throws Exception {
        ServerDetail serverDetail = new ServerDetail();
        serverDetail.setProtocol(Protocol.EPP);
        serverDetail.setServerAddress("192.168.33.15");
        serverDetail.setServerPort(8700);
        serverDetail.setName("devvm");

        ProtocolBootstrap eppProtocolBootstrap = EppProtocolBootstrapFactory.getInstance().newInstance();
        clientConnection = ClientConnection.getInstance(serverDetail, eppProtocolBootstrap);
    }

    @Test
    public void itShouldConnectToRemoteMachine() throws InterruptedException {
        // given
        ChannelFuture sync = clientConnection.connect().sync();
        EppMessageReader eppMessageReader = (EppMessageReader) sync.channel().pipeline().last();
        String message = eppMessageReader.getMessage();

        assertThat(message).isNotNull();
    }

    @Test
    public void itShouldBoPossibleToSendMessageToRemoteMachine() throws InterruptedException {
        // given
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class).handler(
                new ChannelConfigurator());

        Channel ch = b.connect("192.168.33.15", 8700).sync().channel();

        ByteBuf byteBuf = Unpooled.buffer();
        byteBuf.writeBytes(new String("<epp>dupa</epp>").getBytes(CharsetUtil.UTF_8));

        ch.writeAndFlush(byteBuf).sync();
    }
}

package io.zerodi.windbag.app.registry;

import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.CharsetUtil;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.Protocol;
import io.zerodi.windbag.core.protocol.Connection;
import io.zerodi.windbag.core.protocol.epp.ChannelConfigurator;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author zerodi
 */
public class ClientConnectionTest {

	private Connection connection;

	@BeforeMethod
	public void setUp() throws Exception {
		ServerDetail serverDetail = new ServerDetail();
		serverDetail.setProtocol(Protocol.EPP);
		serverDetail.setServerAddress("192.168.33.15");
		serverDetail.setServerPort(8700);
		serverDetail.setId("devvm");
	}

	@Test(enabled = false)
	public void itShouldBoPossibleToSendMessageToRemoteMachine() throws InterruptedException {
		// given
		EventLoopGroup group = new NioEventLoopGroup();
		Bootstrap b = new Bootstrap();
		b.group(group).channel(NioSocketChannel.class).handler(ChannelConfigurator.getInstance());

		Channel ch = b.connect("192.168.33.15", 8700).sync().channel();

		ByteBuf byteBuf = Unpooled.buffer();
		byteBuf.writeBytes("X".getBytes(CharsetUtil.UTF_8));

		ch.writeAndFlush(byteBuf).sync();
		Thread.sleep(1000);
	}
}

package io.zerodi.windbag.core.protocol.epp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;
import io.zerodi.windbag.core.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class EppProtocolBootstrap implements ProtocolBootstrap {
	private static final Logger logger = LoggerFactory.getLogger(EppProtocolBootstrap.class);

	private Bootstrap bootstrap = null;

	private EppProtocolBootstrap() {
		bootstrap = new Bootstrap();
		bootstrap.channel(NioSocketChannel.class);
		bootstrap.option(ChannelOption.SO_KEEPALIVE,
		                 true);

		bootstrap.handler(ChannelConfigurator.getInstance());
	}

	public static EppProtocolBootstrap getInstance() {
		return new EppProtocolBootstrap();
	}

	@Override
	public Protocol getProtocol() {
		return Protocol.EPP;
	}

	@Override
	public Bootstrap getBootstrap() {
		return bootstrap;
	}
}

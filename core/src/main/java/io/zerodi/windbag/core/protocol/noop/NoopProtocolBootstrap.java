package io.zerodi.windbag.core.protocol.noop;

import io.netty.bootstrap.Bootstrap;
import io.zerodi.windbag.app.registry.ProtocolBootstrap;

/**
 * Helper implementation of {@link io.zerodi.windbag.app.registry.ProtocolBootstrap}, designed to do nothing.
 *
 * @author zerodi
 */
public class NoopProtocolBootstrap implements ProtocolBootstrap {

	private Bootstrap bootstrap;

	private NoopProtocolBootstrap() {
		bootstrap = new Bootstrap();
	}

	public static NoopProtocolBootstrap getInstance() {
		return new NoopProtocolBootstrap();
	}

	@Override
	public Bootstrap getBootstrap() {
		return bootstrap;
	}
}

package io.zerodi.windbag.app.client.protocol.epp;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.zerodi.windbag.app.client.protocol.Message;
import io.zerodi.windbag.app.client.registry.ProtocolBootstrap;
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
        bootstrap.option(ChannelOption.SO_KEEPALIVE, true);

        bootstrap.handler(new ChannelInitializer<SocketChannel>() {

            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(EppMessageDecoder.getInstance(), EppMessageReader.getInstance(EppProtocolBootstrap.this));
            }
        });
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

    @Override
    public void onMessage(Message message) {
        logger.info("onMessage: {}", message);
    }
}

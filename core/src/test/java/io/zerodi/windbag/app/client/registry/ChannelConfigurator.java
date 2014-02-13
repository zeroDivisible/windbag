package io.zerodi.windbag.app.client.registry;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.zerodi.windbag.app.client.protocol.epp.EppMessageDecoder;

/**
 * Configures a channel for handling EPP messages.
 * @author zerodi
 */
public class ChannelConfigurator extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("logger", new LoggingHandler(LogLevel.DEBUG));
        pipeline.addLast("length-prepender", new LengthFieldPrepender(4, true));
        pipeline.addLast("epp-message-decoder", EppMessageDecoder.getInstance());
        pipeline.addLast("string-decoder", new StringDecoder());
        pipeline.addLast("string-encoder", new StringEncoder());
        pipeline.addLast("test-handler", new TestHandler());
    }
}

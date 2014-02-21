package io.zerodi.windbag.core.protocol.epp;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

/**
 * Configures a channel for handling EPP messages.
 *
 * @author zerodi
 */
public class ChannelConfigurator extends ChannelInitializer<SocketChannel> {

    private ChannelConfigurator() {
    }

    public static ChannelConfigurator getInstance() {
        return new ChannelConfigurator();
    }

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("message-length-adder", new LengthFieldPrepender(4, true));
        pipeline.addLast("epp-message-decoder", EppMessageDecoder.getInstance());
        pipeline.addLast("string-decoder", new StringDecoder());
        pipeline.addLast("string-encoder", new StringEncoder());
    }
}

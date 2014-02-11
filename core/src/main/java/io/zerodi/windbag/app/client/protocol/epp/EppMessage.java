package io.zerodi.windbag.app.client.protocol.epp;

import io.netty.util.CharsetUtil;
import io.zerodi.windbag.app.client.protocol.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author zerodi
 */
public class EppMessage implements Message {
    private static final Logger logger = LoggerFactory.getLogger(EppMessage.class);

    private final String message;

    private EppMessage(byte[] message) {
        this.message = new String(message, CharsetUtil.UTF_8);
        logger.debug("new EPP Message: {}", message);
    }

    public static Message getInstance(byte[] message) {
        return new EppMessage(message);
    }

    public String getMessage() {
        return message;
    }

    @Override
    public String toString() {
        return "EppMessage{" +
                "message='" + message + '\'' +
                '}';
    }
}

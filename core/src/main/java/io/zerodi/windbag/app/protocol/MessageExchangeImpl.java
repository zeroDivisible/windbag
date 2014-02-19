package io.zerodi.windbag.app.protocol;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Implementation of {@link io.zerodi.windbag.app.protocol.MessageExchange}
 *
 * @author zerodi
 */
public class MessageExchangeImpl implements MessageExchange {
    private static final Logger logger = LoggerFactory.getLogger(MessageExchangeImpl.class);

    private List<Message> messages = new ArrayList<>();

    /**
     * I didn't need any sophisticated communication mechanism, just a simple way to delay the process until a response is provided.
     */
    private BlockingDeque<Object> notificationLocks = new LinkedBlockingDeque<>();

    private MessageExchangeImpl() {
    }

    public static MessageExchange getInstance() {
        return new MessageExchangeImpl();
    }

    @Override
    public void postMessage(Message message) {
        messages.add(message);

        if (!notificationLocks.isEmpty()) {
            Object take = notificationLocks.pollFirst();
            if (take != null) {
                take.notify();
            }
        }
    }

    @Override
    public void offerNotificationObject(Object object) {
        notificationLocks.addFirst(object);
    }

    @Override
    public Message getLastMessage() {
        return messages.get(messages.size() - 1);
    }
}

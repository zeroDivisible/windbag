package io.zerodi.windbag.app.protocol;

/**
 * {@link io.zerodi.windbag.app.protocol.MessageExchange} which is blocking on responses after each message is sent
 *
 * @author zerodi
 */
public class MessageExchangeImpl implements MessageExchange {

    private MessageExchangeImpl() {
    }

    public static MessageExchange getInstance() {
        return new MessageExchangeImpl();
    }

    @Override
    public Response postMessage(Message message) {
        return null; // TODO Implement
    }

    @Override
    public Response getResponse(Message message) {
        return null; // TODO Implement
    }

    @Override
    public Message getMessage(Response response) {
        return null; // TODO Implement
    }

    @Override
    public boolean hasMessage(Message message) {
        return false;  //TODO Implement
    }
}

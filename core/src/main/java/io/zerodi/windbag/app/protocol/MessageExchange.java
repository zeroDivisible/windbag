package io.zerodi.windbag.app.protocol;

/**
 * Netty is brilliant for sending messages to remote servers - the only problem being, that there is no bundled way of dealing with
 * responses. This interface can be understood as a list which helps abstracting the logic of mapping messages to their responses.
 * <p/>
 * TODO: ideally, I think that this should be replaces with a ring buffer maybe, just to limit the size of stored messages
 * <p/>
 * TODO: it should have a methods blocking the process until a response has been delivered.
 *
 * @author zerodi
 */
public interface MessageExchange {

	public void postMessage(Message message);

	/**
	 * This is a simple notification object which will be notified when the next message will reach the exchange.
	 *
	 * @param object
	 */
	public void offerNotificationObject(Object object);

	public Message getLastMessage();
}

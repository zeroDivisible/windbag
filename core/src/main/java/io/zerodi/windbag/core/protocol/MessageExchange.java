package io.zerodi.windbag.core.protocol;

import java.util.List;

/**
 * Netty is brilliant for sending messages to remote servers - the only problem being, that there is no bundled way of dealing with
 * responses. This interface can be understood as a list which helps abstracting the logic of mapping messages to their responses.
 * <p/>
 *
 * @author zerodi
 */
public interface MessageExchange {

	public Message postMessage(Message message);

	public Message getLastMessage();

	public List<Message> getLast(int count);
}

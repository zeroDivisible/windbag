package io.zerodi.windbag.app.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;

/**
 * Implementation of {@link io.zerodi.windbag.app.protocol.MessageExchange}
 *
 * @author zerodi
 */
public class MessageExchangeImpl implements MessageExchange {
	private List<Message> messages = new ArrayList<>();

	private MessageExchangeImpl() {
	}

	public static MessageExchange getInstance() {
		return new MessageExchangeImpl();
	}

	@Override
	public Message postMessage(Message message) {
		messages.add(message);
		return message;
	}

	@Override
	public Message getLastMessage() {
		return messages.get(messages.size() - 1);
	}

	@Override
	public List<Message> getLast(int count) {
		int to = messages.size();
		int from = Math.max(to - count, 0);

		return messages.subList(from , to);
	}
}

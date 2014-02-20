package io.zerodi.windbag.app.protocol;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.BlockingDeque;
import java.util.concurrent.LinkedBlockingDeque;

/**
 * Implementation of {@link io.zerodi.windbag.app.protocol.MessageExchange}
 *
 * @author zerodi
 */
public class MessageExchangeImpl implements MessageExchange {
	private static final Logger logger = LoggerFactory.getLogger(MessageExchangeImpl.class);

	private List<Message> messages = new ArrayList<>();

	private MessageExchangeImpl() {
	}

	public static MessageExchange getInstance() {
		return new MessageExchangeImpl();
	}

	@Override
	public void postMessage(Message message) {
		messages.add(message);
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

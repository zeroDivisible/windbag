package io.zerodi.windbag.core.protocol;

/**
 * Enum which is representing a type of a message - whether it is an incoming message or outgoing one, etc.
 *
 * @author zerodi
 */
public enum MessageType {
	INBOUND,
	OUTBOUND,
	SYSTEM,
	SYSTEM_CONNECTION_ESTABLISHED, SYSTEM_INFO, SYSTEM_CONNECTION_DISCONNECTED,
}

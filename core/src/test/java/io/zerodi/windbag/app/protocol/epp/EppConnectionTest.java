package io.zerodi.windbag.app.protocol.epp;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.MessageExchange;
import io.zerodi.windbag.core.protocol.MessageExchangeImpl;
import io.zerodi.windbag.core.protocol.ProtocolHandler;
import io.zerodi.windbag.core.protocol.epp.EppProtocolHandler;
import org.testng.annotations.BeforeMethod;

/**
 * @author zerodi
 */
public class EppConnectionTest {

	private MessageExchange messageExchange;

	private ServerDetail serverDetail;

	private ProtocolHandler protocolHandler;

	private ApplicationConfiguration applicationConfiguration;

	@BeforeMethod
	public void setUp() throws Exception {
		messageExchange = MessageExchangeImpl.getInstance();
		serverDetail = new ServerDetail();

		protocolHandler = EppProtocolHandler.getInstance(serverDetail, messageExchange, applicationConfiguration);
	}
}

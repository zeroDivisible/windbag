package io.zerodi.windbag.app.protocol.epp;

import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.ApplicationConfiguration;
import io.zerodi.windbag.core.protocol.Handler;
import io.zerodi.windbag.core.protocol.MessageExchange;
import io.zerodi.windbag.core.protocol.MessageExchangeImpl;
import io.zerodi.windbag.core.protocol.epp.EppHandler;
import org.testng.annotations.BeforeMethod;

/**
 * @author zerodi
 */
public class EppConnectionTest {

	private MessageExchange messageExchange;

	private ServerDetail serverDetail;

	private Handler handler;

	private ApplicationConfiguration applicationConfiguration;

	@BeforeMethod
	public void setUp() throws
	                    Exception {
		messageExchange = MessageExchangeImpl.getInstance();
		serverDetail = new ServerDetail();

		handler = EppHandler.getInstance(serverDetail,
		                                 applicationConfiguration);
	}
}

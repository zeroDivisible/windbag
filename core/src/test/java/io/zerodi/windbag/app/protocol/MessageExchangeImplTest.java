package io.zerodi.windbag.app.protocol;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

/**
 * @author zerodi
 */
public class MessageExchangeImplTest {

    private MessageExchange messageExchange;

    @BeforeMethod
    public void setUp() throws Exception {
        messageExchange = MessageExchangeImpl.getInstance();
    }

    @Test
    public void itShouldBePossibleToPostAMessage() {
        // given


    }
}

package io.zerodi.windbag.app.server.netty;

import com.yammer.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 * @author zerodi
 */
public class TestTcpClient implements Managed {
    private static final Logger logger = LoggerFactory.getLogger(TestTcpClient.class);


    private TestTcpClient() {
    }

    public static TestTcpClient getInstance() {
        return new TestTcpClient();
    }

    @Override
    public void start() throws Exception {
        logger.info("starting TestTcpClient");
    }

    @Override
    public void stop() throws Exception {
        logger.info("stopping TestTcpClient");
    }
}

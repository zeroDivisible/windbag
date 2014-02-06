package io.zerodi.windbag.app.server.netty;

import com.yammer.dropwizard.lifecycle.Managed;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 * @author zerodi
 */
public class TestTcpServer implements Managed {
    private static final Logger logger = LoggerFactory.getLogger(TestTcpServer.class);


    private TestTcpServer() {
    }

    public static TestTcpServer getInstance() {
        return new TestTcpServer();
    }

    @Override
    public void start() throws Exception {
        logger.info("starting TestTcpServer");
    }

    @Override
    public void stop() throws Exception {
        logger.info("stopping TestTcpServer");
    }
}

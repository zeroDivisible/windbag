package io.zerodi.windbag.app.registry;

import com.google.common.base.Preconditions;
import com.yammer.dropwizard.lifecycle.Managed;
import io.zerodi.windbag.api.representations.ServerDetail;
import io.zerodi.windbag.core.protocol.Connection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;

/**
 * Simple, test implementation of TcpServer which is getting managed with the lifecycle of the whole stack.
 *
 * @author zerodi
 */
public class ChannelRegistryImpl implements Managed, ChannelRegistry {
    private static final Logger logger = LoggerFactory.getLogger(ChannelRegistryImpl.class);

    private HashMap<String, Connection> connectionHashMap = new HashMap<>();

    private ChannelRegistryImpl() {
    }

    public static ChannelRegistryImpl getInstance() {
        return new ChannelRegistryImpl();
    }

    @Override
    public void start() throws Exception {
        logger.info("starting channel registry");

    }

    @Override
    public void stop() throws Exception {
        logger.info("stopping channel registry");

        Collection<Connection> values = connectionHashMap.values();
        for (Connection connection : values) {
            if (connection.isConnected()) {
                connection.disconnect();
            }
        }
    }

    @Override
    public void registerConnection(Connection connection) {
        Preconditions.checkNotNull(connection.getProtocolBootstrap(), "connection.getProtocolBootstrap() cannot be null!");

        ServerDetail serverDetail = connection.getServerDetail();
        Preconditions.checkNotNull(serverDetail, "connection.getServerDetail() cannot be null!");
        String serverId = serverDetail.getName();
        Preconditions.checkNotNull(serverId, "clientConnection.getServerDetail().getName() cannot be null!");

        if (connectionHashMap.containsKey(serverId)) {
            throw new RuntimeException("already registered " + serverId);
        }

        connectionHashMap.put(serverId, connection);
    }

    @Override
    public Connection getConnection(String serverId) {
        Preconditions.checkNotNull(serverId, "serverId cannot be null!");

        if (!connectionHashMap.containsKey(serverId)) {
            return null;
        }

        return connectionHashMap.get(serverId);
    }

}

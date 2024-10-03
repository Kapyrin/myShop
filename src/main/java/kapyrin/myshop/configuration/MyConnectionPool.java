package kapyrin.myshop.configuration;

import kapyrin.myshop.exception.PoolException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public enum MyConnectionPool {
    INSTANCE;
    private static final String POOL_SIZE_FROM_PROPERTIES = "pool.size";
    private final Logger logger = LogManager.getLogger(MyConnectionPool.class);
    private final ConcurrentLinkedQueue<Connection> availableConnections = new ConcurrentLinkedQueue<>();

    MyConnectionPool() {
        int poolSize = poolSizeFromProperties();
        for (int i = 0; i < poolSize; i++) {
            availableConnections.offer(ConnectionManager.openConnection());
        }
        logger.info("Connection pool has been initialized with size:  " + poolSize);
    }

    public Connection getConnection() throws PoolException {
        if (availableConnections.isEmpty()) {
            throw new PoolException("All connections in a pool are in use!");
        }
        Connection connection = availableConnections.poll();
        return new ConnectionProxy(connection, this);
    }

    public void returnConnection(Connection connection) {
        availableConnections.offer(connection);
        logger.info("Connection returned to the pool.");
    }

    public void closePool() throws PoolException {
        while (!availableConnections.isEmpty()) {
            Connection connection = availableConnections.poll();
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException exception) {
                    throw new PoolException("Failed to close pool connection.", exception);
                }
            }
        }
        logger.info("Connection pool closed.");
    }

    private int poolSizeFromProperties() {
        int poolSizeDefault = 10;
        try {
            String poolSize = DBConfigFromProperties.getKey(POOL_SIZE_FROM_PROPERTIES);
            if (poolSize != null) {
                poolSizeDefault = Integer.parseInt(poolSize);
            }
        } catch (NumberFormatException | NullPointerException e) {
            logger.warn("Invalid pool size from properties file. Defaulting to:  {},  {}", poolSizeDefault, e.getMessage());
        }
        logger.info("Using pool size: {}", poolSizeDefault);
        return poolSizeDefault;
    }
}
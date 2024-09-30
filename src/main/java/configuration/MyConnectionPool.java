package configuration;

import exception.PoolException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.concurrent.ConcurrentLinkedQueue;

public enum MyConnectionPool {
    INSTANCE;

    private static final int POOL_SIZE = 10;
    private final ConcurrentLinkedQueue<Connection> availableConnections = new ConcurrentLinkedQueue<>();

    MyConnectionPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            availableConnections.offer(ConnectionManager.openConnection());
        }
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
        System.out.println("Connection returned to the pool.");
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
            System.out.println("Connection pool closed.");
        }
    }
}
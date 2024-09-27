package configuration;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public enum MyConnectionPool {
    INSTANCE;

    private static final int POOL_SIZE = 10;
    private final List<Connection> availableConnections = new ArrayList<>();

    MyConnectionPool() {
        for (int i = 0; i < POOL_SIZE; i++) {
            availableConnections.add(ConnectionManager.openConnection());
        }
    }

    public synchronized Connection getConnection() {
        if (availableConnections.isEmpty()) {
            throw new RuntimeException("All connections in a pool are in use!");
        }
        Connection connection = availableConnections.remove(availableConnections.size() - 1);
        return new ConnectionProxy(connection, this);
    }

    public synchronized void returnConnection(Connection connection) {
        availableConnections.add(connection);
        System.out.println("Connection returned to the pool.");
    }

    public synchronized void closePool() throws SQLException {
        for (Connection connection : availableConnections) {
            connection.close();
        }
        availableConnections.clear();
        System.out.println("Connection pool closed.");
    }
}

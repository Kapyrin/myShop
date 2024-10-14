package kapyrin.myshop.configuration;

import kapyrin.myshop.exception.ConnectionException;
import kapyrin.myshop.exception.LoadDriverException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public enum ConnectionManager {
    INSTANCE;
    private static final String PASSWORD_KEY = "db.password";
    private static final String USERNAME_KEY = "db.username";
    private static final String URL_KEY = "db.url";
    private static final String DRIVER_CLASS_NAME = "db.driver";

    static {
        loadDriver();
    }


    private static void loadDriver() {
        try {
            Class.forName(DBConfigFromProperties.getKey(DRIVER_CLASS_NAME));
        } catch (ClassNotFoundException e) {
            throw new LoadDriverException("Could not load JDBC driver", e);
        }
    }

    public static Connection openConnection() {
        try {
            return DriverManager.getConnection(
                    DBConfigFromProperties.getKey(URL_KEY),
                    DBConfigFromProperties.getKey(USERNAME_KEY),
                    DBConfigFromProperties.getKey(PASSWORD_KEY)
            );

        } catch (SQLException e) {
            throw new ConnectionException("Could not open connection", e);
        }
    }
}

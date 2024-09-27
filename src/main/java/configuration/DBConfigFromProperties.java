package configuration;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;


public final class DBConfigFromProperties {
    private static final String DB_PROPERTIES_FILE = "application.properties";
    private static final Properties PROPERTIES = new Properties();

    private DBConfigFromProperties() {
    }

    static {
        loadProperties();
    }

    private static void loadProperties() {
        try (InputStream resourceAsStream = DBConfigFromProperties.class.getClassLoader().getResourceAsStream(DB_PROPERTIES_FILE)) {
            PROPERTIES.load(resourceAsStream);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getKey(String key) {
        return PROPERTIES.getProperty(key);
    }

}

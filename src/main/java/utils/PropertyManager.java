package utils;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class PropertyManager {

    private static final Properties properties;

    static {
        properties = new Properties();
        try {
            properties.load(new FileReader("src/main/resources/env.properties"));
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }
    }

    public String getPropertyValue(String key) {
        return properties.getProperty(key);
    }
}

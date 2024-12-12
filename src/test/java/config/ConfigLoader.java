package config;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigLoader {
    private static final Properties properties = new Properties();


    static {
        String env = System.getProperty("ENV", "local");
        String propertiesFileName = "conf-" + env + ".properties";

        try (InputStream input = ConfigLoader.class.getClassLoader().getResourceAsStream(propertiesFileName)) {
            if (input == null) {
                throw new IOException("Файл свойств '" + propertiesFileName + "' не найден в classpath");
            }
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }
}
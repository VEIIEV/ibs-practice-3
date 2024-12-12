package config;

import org.aeonbits.owner.ConfigFactory;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DbConfig {



    public static Connection getConnection() {
        String url = ConfigLoader.getProperty("db.url");
        String username = ConfigLoader.getProperty("db.login");
        String password = ConfigLoader.getProperty("db.password");
        try {
            return DriverManager.getConnection(url, username, password);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }


    public static void closeConnection(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

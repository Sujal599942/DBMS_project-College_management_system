

package com.college.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class DBConnection {

    private static final Properties properties = new Properties();

    static {
        try (FileInputStream input =
                new FileInputStream("config.properties")) {

            properties.load(input);

        } catch (IOException e) {
            throw new ExceptionInInitializerError(
                "Could not load config.properties: " + e.getMessage()
            );
        }
    }

    public static Connection getConnection() throws SQLException {

        String url = properties.getProperty("db.url");
        String username = properties.getProperty("db.username");
        String password = properties.getProperty("db.password");

        if (url == null || username == null || password == null) {
            throw new SQLException(
                "Database configuration is incomplete."
            );
        }

        return DriverManager.getConnection(url, username, password);
    }
}

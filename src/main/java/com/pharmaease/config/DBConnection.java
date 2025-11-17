package com.pharmaease.config;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DBConnection {

    private static Connection connection = null;

    // Load DB credentials from config.properties only once
    static {
        try {
            Properties properties = new Properties();
            InputStream inputStream = DBConnection.class.getClassLoader()
                    .getResourceAsStream("config.properties");

            if (inputStream == null) {
                throw new RuntimeException("config.properties file not found in resources folder!");
            }

            properties.load(inputStream);

            String url = properties.getProperty("db.url");
            String username = properties.getProperty("db.username");
            String password = properties.getProperty("db.password");

            // Load MySQL driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Create connection
            connection = DriverManager.getConnection(url, username, password);
            System.out.println("✅ Database connected successfully!");

        } catch (IOException e) {
            System.out.println("❌ Error loading config.properties: " + e.getMessage());
        } catch (ClassNotFoundException e) {
            System.out.println("❌ MySQL JDBC Driver not found: " + e.getMessage());
        } catch (SQLException e) {
            System.out.println("❌ Database connection error: " + e.getMessage());
        }
    }

    // Return a single shared connection (Singleton)
    public static Connection getConnection() {
        return connection;
    }
}

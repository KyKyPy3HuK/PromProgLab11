package org.example.connection;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionManager {
    private static final String URL;
    private static final String URLE;
    private static final String USERNAME;
    private static final String PASSWORD;

    static {
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Properties properties = new Properties();
            InputStream in = Files.newInputStream(Path.of("C:\\Приколы\\StudFiles\\Учеба\\ПромышленноеПрограммирование\\Лабы\\11\\PromProgLab11\\src\\main\\resources\\DBproperties.properties"));
            properties.load(in);
            URLE=  properties.getProperty("driver");
            URL = properties.getProperty("url");
            USERNAME = properties.getProperty("username");
            PASSWORD = properties.getProperty("password");
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static Connection open(){
        try {
            return DriverManager.getConnection(URL,USERNAME,PASSWORD);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}

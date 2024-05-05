package org.example;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnection {
    private static Connection connection = null;

    public static Connection getConnection() {
        try {
            connection = DriverManager.getConnection("${DB_URL}", "${DB_USERNAME}", "${DB_PASSWORD}");
            System.out.println("Connection database has successfully !");
        } catch (SQLException e) {
            System.out.println("Connection database failure");
            throw new RuntimeException(e);
        }
        return connection;
    }


    public static void closeConnection() {

        try {
            if (connection != null) {
                connection.close();
                System.out.println("Database connection closed successfully!");
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);

        }
    }

}

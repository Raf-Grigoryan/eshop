package eshop.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DBConnectionProvider {

    private static DBConnectionProvider instance;

    private Connection connection;

    private static final String URL = "jdbc:mysql://localhost:3306/eshop";
    private static final String USER = "root";
    private static final String PASSWORD = "rootroot";

    public static DBConnectionProvider getInstance() {
        if (instance == null) {
            instance = new DBConnectionProvider();
        }
        return instance;
    }

    public Connection getConnection() {
        try {
            if (connection == null) {
                connection = DriverManager.getConnection(URL, USER, PASSWORD);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return connection;
    }
}

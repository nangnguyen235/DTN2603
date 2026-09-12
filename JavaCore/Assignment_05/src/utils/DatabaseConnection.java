package utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
    private static final String URL = "jdbc:mysql://localhost:3306/dtn2603_testing_system";
    private static final String USER = "root";
    private static final String PASSWORD = "1234";

    static {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("Không tìm thấy MySQL JDBC Driver: " + e.getMessage());
        }
    }

    public static Connection getConnection() throws SQLException {
        Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
        if (connection != null) {
            System.out.println("Kết nối DB thành công");
        } else {
            System.out.println("Kết nối DB không thành công");
        }
        return connection;
    }
}

package utils;

import java.sql.Connection;
import java.sql.DriverManager;

public class JDBCUtils {
    private Connection connection;
    // hàm kết nối đến DB
    public static Connection getConnection() {
        Connection connection = null;
        String url = "jdbc:mysql://localhost:3306/dtn2603_testing_system";
        String username = "root";
        String password = "1234";
        try {
            connection = DriverManager.getConnection(url, username, password);
        } catch (Exception e) {
            System.out.println("Kết nối không thành công");
            e.printStackTrace();
        }
        return connection;
    }

    // ngắt kết nối
    public static void closeConnection(Connection connection) {
        if (connection != null) {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
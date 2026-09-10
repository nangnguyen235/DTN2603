package backend;

import entity.Department;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QLDepartment implements IQLDepartment {

    @Override
    public void hienThiThongTin() {
        List<Department> list = new ArrayList<>();
        String url = "jdbc:mysql://localhost:3306/dtn2603_testing_system";
        String username = "root";
        String password = "1234";

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
        } catch (ClassNotFoundException ignored) {
        }

        try (Connection connection = DriverManager.getConnection(url, username, password)) {
            if (connection != null) {
                System.out.println("Kết nối DB thành công");
            } else {
                System.out.println("Kết nối DB không thành công");
                return;
            }

            String sql = "SELECT * FROM department;";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

                ResultSetMetaData rsmd = resultSet.getMetaData();
                int colCount = rsmd.getColumnCount();

                while (resultSet.next()) {
                    int id = getIntVal(resultSet, colCount, rsmd, "department_id", "departmentid", "id");
                    String name = getStringVal(resultSet, colCount, rsmd, "department_name", "departmentname", "name");
                    Department dept = new Department(id, name);
                    list.add(dept);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối hoặc truy vấn CSDL: " + e.getMessage());
        }

        // Hiển thị dạng bảng
        System.out.println("+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |\n", "ID", "Department Name");
        System.out.println("+--------------------+--------------------+");
        if (list.size() > 0) {
            for (Department dept : list) {
                System.out.println(dept);
            }
        } else {
            System.out.printf("|%39s |\n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+");
    }

    private int getIntVal(ResultSet rs, int colCount, ResultSetMetaData rsmd, String... candidateCols) throws SQLException {
        for (String col : candidateCols) {
            for (int i = 1; i <= colCount; i++) {
                if (col.equalsIgnoreCase(rsmd.getColumnLabel(i)) || col.equalsIgnoreCase(rsmd.getColumnName(i))) {
                    return rs.getInt(i);
                }
            }
        }
        return rs.getInt(1);
    }

    private String getStringVal(ResultSet rs, int colCount, ResultSetMetaData rsmd, String... candidateCols) throws SQLException {
        for (String col : candidateCols) {
            for (int i = 1; i <= colCount; i++) {
                if (col.equalsIgnoreCase(rsmd.getColumnLabel(i)) || col.equalsIgnoreCase(rsmd.getColumnName(i))) {
                    return rs.getString(i);
                }
            }
        }
        return rs.getString(colCount >= 2 ? 2 : 1);
    }
}
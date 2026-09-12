package backend;

import entity.Department;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QLDepartment implements IQLDepartment {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void hienThiThongTin() {
        List<Department> list = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM department;";
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet resultSet = ps.executeQuery()) {

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
        System.out.printf("|%19s |%19s |%n", "ID", "Department Name");
        System.out.println("+--------------------+--------------------+");
        if (list.size() > 0) {
            for (Department dept : list) {
                System.out.println(dept);
            }
        } else {
            System.out.printf("|%39s |%n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+");
    }

    @Override
    public void themDepartment() {
        System.out.println("Nhập tên department:");
        String departmentName = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO department (department_name) VALUES (?);";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, departmentName);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Thêm department thành công!");
                } else {
                    System.out.println("Thêm department không thành công!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm department: " + e.getMessage());
        }
    }

    @Override
    public void xoaDepartmentTheoId() {
        System.out.println("Nhập ID department muốn xóa:");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM department WHERE department_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Xóa department thành công!");
                } else {
                    System.out.println("Không tìm thấy department có ID = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa department: " + e.getMessage());
        }
    }

    @Override
    public void suaDepartmentNameTheoId() {
        System.out.println("Nhập ID department muốn sửa:");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
            return;
        }
        System.out.println("Nhập tên department mới:");
        String newName = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE department SET department_name = ? WHERE department_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, newName);
                ps.setInt(2, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Sửa department thành công!");
                } else {
                    System.out.println("Không tìm thấy department có ID = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi sửa department: " + e.getMessage());
        }
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
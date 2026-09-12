package backend;

import entity.Account;
import entity.Department;
import entity.Position;
import entity.enums.PositionName;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QLAccount implements IQLAccount {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void hienThiThongTin() {
        List<Account> list = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT a.*, d.department_name, p.position_name " +
                         "FROM `account` a " +
                         "LEFT JOIN `department` d ON a.department_id = d.department_id " +
                         "LEFT JOIN `position` p ON a.position_id = p.position_id;";

            try {
                executeQueryAndFillList(connection, sql, list);
            } catch (SQLException e) {
                // Fallback truy vấn đơn giản từ bảng account
                String fallbackSql = "SELECT * FROM `account`;";
                try {
                    executeQueryAndFillList(connection, fallbackSql, list);
                } catch (SQLException ex) {
                    System.out.println("Lỗi truy vấn CSDL: " + ex.getMessage());
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối CSDL: " + e.getMessage());
        }

        // Hiển thị dạng bảng
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |%n",
                "ID", "Email", "Username", "Full Name", "Department ID", "Department Name", "Position ID", "Position Name", "Create Date");
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");

        if (list.size() > 0) {
            for (Account acc : list) {
                System.out.println(acc);
            }
        } else {
            System.out.printf("|%179s |%n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
    }

    @Override
    public void themAccount() {
        System.out.println("Nhập email:");
        String email = scanner.nextLine();

        System.out.println("Nhập username:");
        String username = scanner.nextLine();

        System.out.println("Nhập full name:");
        String fullName = scanner.nextLine();

        System.out.println("Nhập department_id:");
        int departmentId;
        try {
            departmentId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Department ID không hợp lệ!");
            return;
        }

        System.out.println("Nhập position_id:");
        int positionId;
        try {
            positionId = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Position ID không hợp lệ!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO account (email, username, full_name, department_id, position_id, create_date) VALUES (?, ?, ?, ?, ?, ?);";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, email);
                ps.setString(2, username);
                ps.setString(3, fullName);
                ps.setInt(4, departmentId);
                ps.setInt(5, positionId);
                ps.setDate(6, java.sql.Date.valueOf(LocalDate.now()));
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Thêm account thành công!");
                } else {
                    System.out.println("Thêm account không thành công!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm account: " + e.getMessage());
        }
    }

    @Override
    public void xoaAccountTheoId() {
        System.out.println("Nhập ID account muốn xóa:");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM account WHERE account_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Xóa account thành công!");
                } else {
                    System.out.println("Không tìm thấy account có ID = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa account: " + e.getMessage());
        }
    }

    @Override
    public void suaUsernameTheoId() {
        System.out.println("Nhập ID account muốn sửa:");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
            return;
        }
        System.out.println("Nhập username mới:");
        String newUsername = scanner.nextLine();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE account SET username = ? WHERE account_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, newUsername);
                ps.setInt(2, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Sửa username thành công!");
                } else {
                    System.out.println("Không tìm thấy account có ID = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi sửa username: " + e.getMessage());
        }
    }

    private void executeQueryAndFillList(Connection connection, String sql, List<Account> list) throws SQLException {
        try (PreparedStatement ps = connection.prepareStatement(sql);
             ResultSet resultSet = ps.executeQuery()) {

            ResultSetMetaData rsmd = resultSet.getMetaData();
            int colCount = rsmd.getColumnCount();

            while (resultSet.next()) {
                int accId = getIntVal(resultSet, colCount, rsmd, "account_id", "accountid", "id");
                String email = getStringVal(resultSet, colCount, rsmd, "email");
                String user_name = getStringVal(resultSet, colCount, rsmd, "user_name", "username");
                String full_name = getStringVal(resultSet, colCount, rsmd, "full_name", "fullname");

                int deptId = getIntVal(resultSet, colCount, rsmd, "department_id", "departmentid");
                String deptName = getStringVal(resultSet, colCount, rsmd, "department_name", "departmentname");
                Department dept = (deptId > 0 || deptName != null) ? new Department(deptId, deptName) : null;

                int posId = getIntVal(resultSet, colCount, rsmd, "position_id", "positionid");
                String posNameStr = getStringVal(resultSet, colCount, rsmd, "position_name", "positionname");
                PositionName posNameEnum = parsePositionName(posNameStr);
                Position pos = (posId > 0 || posNameEnum != null) ? new Position(posId, posNameEnum) : null;

                LocalDate createDate = getLocalDateVal(resultSet, colCount, rsmd, "create_date", "createdate", "created_at");

                Account acc = new Account(accId, email, user_name, full_name, dept, pos, createDate);
                list.add(acc);
            }
        }
    }

    private PositionName parsePositionName(String str) {
        if (str == null || str.trim().isEmpty()) return null;
        String normalized = str.trim().toUpperCase().replace(" ", "_").replace("-", "_");
        try {
            return PositionName.valueOf(normalized);
        } catch (IllegalArgumentException e) {
            for (PositionName pn : PositionName.values()) {
                if (pn.name().equalsIgnoreCase(normalized)) {
                    return pn;
                }
            }
            return null;
        }
    }

    private int getIntVal(ResultSet rs, int colCount, ResultSetMetaData rsmd, String... candidateCols) {
        for (String col : candidateCols) {
            for (int i = 1; i <= colCount; i++) {
                try {
                    if (col.equalsIgnoreCase(rsmd.getColumnLabel(i)) || col.equalsIgnoreCase(rsmd.getColumnName(i))) {
                        return rs.getInt(i);
                    }
                } catch (Exception ignored) {}
            }
        }
        return 0;
    }

    private String getStringVal(ResultSet rs, int colCount, ResultSetMetaData rsmd, String... candidateCols) {
        for (String col : candidateCols) {
            for (int i = 1; i <= colCount; i++) {
                try {
                    if (col.equalsIgnoreCase(rsmd.getColumnLabel(i)) || col.equalsIgnoreCase(rsmd.getColumnName(i))) {
                        return rs.getString(i);
                    }
                } catch (Exception ignored) {}
            }
        }
        return null;
    }

    private LocalDate getLocalDateVal(ResultSet rs, int colCount, ResultSetMetaData rsmd, String... candidateCols) {
        for (String col : candidateCols) {
            for (int i = 1; i <= colCount; i++) {
                try {
                    if (col.equalsIgnoreCase(rsmd.getColumnLabel(i)) || col.equalsIgnoreCase(rsmd.getColumnName(i))) {
                        java.sql.Date sqlDate = rs.getDate(i);
                        if (sqlDate != null) {
                            return sqlDate.toLocalDate();
                        }
                    }
                } catch (Exception ignored) {}
            }
        }
        return null;
    }
}

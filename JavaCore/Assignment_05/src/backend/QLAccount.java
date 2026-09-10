package backend;

import entity.Account;
import entity.Department;
import entity.Position;
import entity.enums.PositionName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QLAccount implements IQLAccount {

    @Override
    public void hienThiThongTin() {
        List<Account> list = new ArrayList<>();
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

            String sql = "SELECT a.*, d.department_name, p.position_name " +
                         "FROM `account` a " +
                         "LEFT JOIN `department` d ON a.department_id = d.department_id " +
                         "LEFT JOIN `position` p ON a.position_id = p.position_id;";
            
            try {
                executeQueryAndFillList(connection, sql, list);
            } catch (SQLException e) {
                // Nếu câu query join lỗi (do tên cột/bảng có thể khác đôi chút), fallback truy vấn đơn giản từ bảng account
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
        System.out.printf("|%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |\n",
                "ID", "Email", "Username", "Full Name", "Department ID", "Department Name", "Position ID", "Position Name", "Create Date");
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");

        if (list.size() > 0) {
            for (Account acc : list) {
                System.out.println(acc);
            }
        } else {
            System.out.printf("|%179s |\n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
    }

    private void executeQueryAndFillList(Connection connection, String sql, List<Account> list) throws SQLException {
        try (Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(sql)) {

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
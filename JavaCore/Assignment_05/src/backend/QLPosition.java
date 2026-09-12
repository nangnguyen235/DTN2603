package backend;

import entity.Position;
import entity.enums.PositionName;
import utils.DatabaseConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QLPosition implements IQLPosition {
    private Scanner scanner = new Scanner(System.in);

    @Override
    public void hienThiThongTin() {
        List<Position> list = new ArrayList<>();

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "SELECT * FROM position;";
            try (PreparedStatement ps = connection.prepareStatement(sql);
                 ResultSet resultSet = ps.executeQuery()) {

                ResultSetMetaData rsmd = resultSet.getMetaData();
                int colCount = rsmd.getColumnCount();

                while (resultSet.next()) {
                    int id = getIntVal(resultSet, colCount, rsmd, "position_id", "positionid", "id");
                    String posNameStr = getStringVal(resultSet, colCount, rsmd, "position_name", "positionname", "name");
                    PositionName posNameEnum = parsePositionName(posNameStr);

                    Position pos = new Position(id, posNameEnum);
                    list.add(pos);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi kết nối hoặc truy vấn CSDL: " + e.getMessage());
        }

        // Hiển thị dạng bảng
        System.out.println("+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |%n", "ID", "Position Name");
        System.out.println("+--------------------+--------------------+");
        if (list.size() > 0) {
            for (Position pos : list) {
                System.out.println(pos);
            }
        } else {
            System.out.printf("|%39s |%n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+");
    }

    @Override
    public void themPosition() {
        System.out.println("Chọn position name:");
        PositionName[] values = PositionName.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        System.out.println("Nhập số tương ứng:");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }
        if (choice < 1 || choice > values.length) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }
        PositionName selectedName = values[choice - 1];

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "INSERT INTO position (position_name) VALUES (?);";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, selectedName.name());
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Thêm position thành công!");
                } else {
                    System.out.println("Thêm position không thành công!");
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi thêm position: " + e.getMessage());
        }
    }

    @Override
    public void xoaPositionTheoId() {
        System.out.println("Nhập ID position muốn xóa:");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
            return;
        }

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "DELETE FROM position WHERE position_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setInt(1, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Xóa position thành công!");
                } else {
                    System.out.println("Không tìm thấy position có ID = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi xóa position: " + e.getMessage());
        }
    }

    @Override
    public void suaPositionNameTheoId() {
        System.out.println("Nhập ID position muốn sửa:");
        int id;
        try {
            id = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("ID không hợp lệ!");
            return;
        }
        System.out.println("Chọn position name mới:");
        PositionName[] values = PositionName.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        System.out.println("Nhập số tương ứng:");
        int choice;
        try {
            choice = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }
        if (choice < 1 || choice > values.length) {
            System.out.println("Lựa chọn không hợp lệ!");
            return;
        }
        PositionName selectedName = values[choice - 1];

        try (Connection connection = DatabaseConnection.getConnection()) {
            String sql = "UPDATE position SET position_name = ? WHERE position_id = ?;";
            try (PreparedStatement ps = connection.prepareStatement(sql)) {
                ps.setString(1, selectedName.name());
                ps.setInt(2, id);
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected > 0) {
                    System.out.println("Sửa position thành công!");
                } else {
                    System.out.println("Không tìm thấy position có ID = " + id);
                }
            }
        } catch (SQLException e) {
            System.out.println("Lỗi khi sửa position: " + e.getMessage());
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
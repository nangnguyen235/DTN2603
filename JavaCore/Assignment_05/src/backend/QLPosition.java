package backend;

import entity.Position;
import entity.enums.PositionName;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class QLPosition implements IQLPosition {

    @Override
    public void hienThiThongTin() {
        List<Position> list = new ArrayList<>();
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

            String sql = "SELECT * FROM position;";
            try (Statement statement = connection.createStatement();
                 ResultSet resultSet = statement.executeQuery(sql)) {

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
        System.out.printf("|%19s |%19s |\n", "ID", "Position Name");
        System.out.println("+--------------------+--------------------+");
        if (list.size() > 0) {
            for (Position pos : list) {
                System.out.println(pos);
            }
        } else {
            System.out.printf("|%39s |\n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+");
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
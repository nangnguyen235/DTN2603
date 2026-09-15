package backend.repository.impl;

import backend.repository.IQLPositionRepository;
import entity.Position;
import entity.enums.PositionName;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QLPositionRepositoryImpl implements IQLPositionRepository {
    @Override
    public List<Position> getAll() {
        List<Position> list = new ArrayList<>();

        try {
            Connection connection = JDBCUtils.getConnection();

            String sql = "SELECT * FROM position;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("position_id");
                String posNameStr = resultSet.getString("position_name");
                PositionName posNameEnum = parsePositionName(posNameStr);

                Position pos = new Position(id, posNameEnum);
                list.add(pos);
            }
            JDBCUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean themPosition(String positionName) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "INSERT INTO position (position_name) VALUES (?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, positionName);

            int c = ps.executeUpdate();
            JDBCUtils.closeConnection(connection);
            return c > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean xoaPositionTheoId(int id) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "DELETE FROM position WHERE position_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setInt(1, id);

            int c = ps.executeUpdate();
            JDBCUtils.closeConnection(connection);
            return c > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean suaPositionNameTheoId(int id, String newPositionName) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "UPDATE position SET position_name = ? WHERE position_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newPositionName);
            ps.setInt(2, id);

            int c = ps.executeUpdate();
            JDBCUtils.closeConnection(connection);
            return c > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
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
}

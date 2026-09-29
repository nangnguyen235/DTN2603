package backend.repository.impl;

import backend.repository.IQLAccountRepository;
import entity.Account;
import entity.Department;
import entity.Position;
import entity.enums.PositionName;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class QLAccountRepositoryImpl implements IQLAccountRepository {

    // ==================== Generic methods ====================

    @Override
    public List<Account> getAll() {
        List<Account> list = new ArrayList<>();

        try {
            Connection connection = JDBCUtils.getConnection();

            String sql = "SELECT a.*, d.department_name, p.position_name " +
                         "FROM `account` a " +
                         "LEFT JOIN `department` d ON a.department_id = d.department_id " +
                         "LEFT JOIN `position` p ON a.position_id = p.position_id;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int accId = resultSet.getInt("account_id");
                String email = resultSet.getString("email");
                String username = resultSet.getString("username");
                String fullName = resultSet.getString("full_name");

                int deptId = resultSet.getInt("department_id");
                String deptName = resultSet.getString("department_name");
                Department dept = new Department(deptId, deptName);

                int posId = resultSet.getInt("position_id");
                String posNameStr = resultSet.getString("position_name");
                PositionName posNameEnum = parsePositionName(posNameStr);
                Position pos = new Position(posId, posNameEnum);

                java.sql.Date sqlDate = resultSet.getDate("create_date");
                LocalDate createDate = (sqlDate != null) ? sqlDate.toLocalDate() : null;

                Account acc = new Account(accId, email, username, fullName, dept, pos, createDate);
                list.add(acc);
            }
            JDBCUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean create(Account entity) {
        // dùng themAccount với các field từ entity
        return themAccount(
                entity.getEmail(),
                entity.getUsername(),
                entity.getFullName(),
                entity.getDepartment() != null ? entity.getDepartment().getDepartmentId() : 0,
                entity.getPosition() != null ? entity.getPosition().getPositionId() : 0
        );
    }

    @Override
    public boolean deleteById(Integer id) {
        return xoaAccountTheoId(id);
    }

    @Override
    public boolean update(Integer id, Account entity) {
        return suaUsernameTheoId(id, entity.getUsername());
    }

    @Override
    public boolean checkExist(Integer id) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            String sql = "SELECT * FROM account WHERE account_id = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setInt(1, id);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return true;// tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(connection);
        }
        return false;
    }

    // ==================== Account-specific methods ====================

    @Override
    public boolean themAccount(String email, String username, String fullName, int departmentId, int positionId) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "INSERT INTO account (email, username, full_name, department_id, position_id, create_date) VALUES (?, ?, ?, ?, ?, ?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, email);
            ps.setString(2, username);
            ps.setString(3, fullName);
            ps.setInt(4, departmentId);
            ps.setInt(5, positionId);
            ps.setDate(6, java.sql.Date.valueOf(LocalDate.now()));

            int c = ps.executeUpdate();
            JDBCUtils.closeConnection(connection);
            return c > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean xoaAccountTheoId(int id) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "DELETE FROM account WHERE account_id = ?;";
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
    public boolean suaUsernameTheoId(int id, String newUsername) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "UPDATE account SET username = ? WHERE account_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newUsername);
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

    @Override
    public boolean checkExistByEmail(String email) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            String sql = "SELECT * FROM account WHERE email = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                return true;// tồn tại
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            JDBCUtils.closeConnection(connection);
        }
        return false;
    }
}

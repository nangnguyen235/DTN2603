package backend.repository.impl;

import backend.repository.IQLDepartmentRepository;
import entity.Department;
import utils.JDBCUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class QLDepartmentRepositoryImpl implements IQLDepartmentRepository {

    // ==================== Generic methods ====================

    @Override
    public List<Department> getAll() {
        List<Department> list = new ArrayList<>();

        try {
            Connection connection = JDBCUtils.getConnection();

            String sql = "SELECT * FROM department;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                int id = resultSet.getInt("department_id");
                String name = resultSet.getString("department_name");
                Department dept = new Department(id, name);
                list.add(dept);
            }
            JDBCUtils.closeConnection(connection);
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return list;
    }

    @Override
    public boolean create(Department entity) {
        return themDepartment(entity.getDepartmentName());
    }

    @Override
    public boolean deleteById(Integer id) {
        return xoaDepartmentTheoId(id);
    }

    @Override
    public boolean update(Integer id, Department entity) {
        return suaDepartmentNameTheoId(id, entity.getDepartmentName());
    }

    @Override
    public boolean checkExist(Integer id) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            String sql = "SELECT * FROM department WHERE department_id = ?";
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

    // ==================== Department-specific methods ====================

    @Override
    public boolean themDepartment(String departmentName) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "INSERT INTO department (department_name) VALUES (?);";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, departmentName);

            int c = ps.executeUpdate();
            JDBCUtils.closeConnection(connection);
            return c > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean xoaDepartmentTheoId(int id) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "DELETE FROM department WHERE department_id = ?;";
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
    public boolean suaDepartmentNameTheoId(int id, String newName) {
        try {
            Connection connection = JDBCUtils.getConnection();
            String sql = "UPDATE department SET department_name = ? WHERE department_id = ?;";
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, newName);
            ps.setInt(2, id);

            int c = ps.executeUpdate();
            JDBCUtils.closeConnection(connection);
            return c > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public boolean checkExistByName(String departmentName) {
        Connection connection = null;
        try {
            connection = JDBCUtils.getConnection();

            String sql = "SELECT * FROM department WHERE department_name = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, departmentName);

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

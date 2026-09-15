package backend.service;

import entity.Department;

import java.util.List;

public interface IQLDepartmentService {
    List<Department> getAll();

    boolean themDepartment(String departmentName);

    boolean xoaDepartmentTheoId(int id);

    boolean suaDepartmentNameTheoId(int id, String newName);
}

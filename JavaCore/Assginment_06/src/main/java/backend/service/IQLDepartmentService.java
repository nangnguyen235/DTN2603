package backend.service;

import entity.Department;

import java.util.List;

public interface IQLDepartmentService extends IGenericService<Department, Integer> {
    // Kế thừa từ IGenericService: getAll(), create(), deleteById(), update(), checkExist(), importCSV()

    // Các method đặc thù cho Department
    boolean themDepartment(String departmentName);

    boolean xoaDepartmentTheoId(int id);

    boolean suaDepartmentNameTheoId(int id, String newName);
}

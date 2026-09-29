package backend.repository;

import entity.Department;

import java.util.List;

public interface IQLDepartmentRepository extends IGenericRepository<Department, Integer> {
    // Kế thừa từ IGenericRepository: getAll(), create(), deleteById(), update(), checkExist()

    // Các method đặc thù cho Department
    boolean themDepartment(String departmentName);

    boolean xoaDepartmentTheoId(int id);

    boolean suaDepartmentNameTheoId(int id, String newName);

    boolean checkExistByName(String departmentName);
}

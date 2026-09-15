package backend.service.impl;

import backend.repository.IQLDepartmentRepository;
import backend.repository.impl.QLDepartmentRepositoryImpl;
import backend.service.IQLDepartmentService;
import entity.Department;

import java.util.List;

public class QLDepartmentServiceImpl implements IQLDepartmentService {
    private IQLDepartmentRepository repository;

    public QLDepartmentServiceImpl() {
        repository = new QLDepartmentRepositoryImpl();
    }

    @Override
    public List<Department> getAll() {
        List<Department> departments = repository.getAll();
        return departments;
    }

    @Override
    public boolean themDepartment(String departmentName) {
        return repository.themDepartment(departmentName);
    }

    @Override
    public boolean xoaDepartmentTheoId(int id) {
        return repository.xoaDepartmentTheoId(id);
    }

    @Override
    public boolean suaDepartmentNameTheoId(int id, String newName) {
        return repository.suaDepartmentNameTheoId(id, newName);
    }
}

package backend.controller;

import backend.service.IQLDepartmentService;
import backend.service.impl.QLDepartmentServiceImpl;
import entity.Department;

import java.util.List;

public class QLDepartmentController {
    private IQLDepartmentService service;

    public QLDepartmentController() {
        service = new QLDepartmentServiceImpl();
    }

    public List<Department> getAll() {
        List<Department> departments = service.getAll();
        return departments;
    }

    public boolean themDepartment(String departmentName) {
        return service.themDepartment(departmentName);
    }

    public boolean xoaDepartmentTheoId(int id) {
        return service.xoaDepartmentTheoId(id);
    }

    public boolean suaDepartmentNameTheoId(int id, String newName) {
        return service.suaDepartmentNameTheoId(id, newName);
    }

    public String importCSV(String url) {
        return service.importCSV(url);
    }
}

package backend.service.impl;

import backend.repository.IGenericRepository;
import backend.repository.IQLDepartmentRepository;
import backend.repository.impl.QLDepartmentRepositoryImpl;
import backend.service.IQLDepartmentService;
import entity.Department;

import java.util.ArrayList;
import java.util.List;

public class QLDepartmentServiceImpl extends AbstractGenericService<Department, Integer> implements IQLDepartmentService {
    private IQLDepartmentRepository repository;

    public QLDepartmentServiceImpl() {
        repository = new QLDepartmentRepositoryImpl();
    }

    @Override
    protected IGenericRepository<Department, Integer> getRepository() {
        return repository;
    }

    // ==================== Department-specific methods ====================

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

    // ==================== CSV Import (implement ICsvImportable) ====================

    /**
     * Validate và parse 1 dòng CSV thành Department
     * Format CSV: department_name
     */
    @Override
    public String validateAndParse(String line, List<Department> validEntities) {
        List<String> errors = new ArrayList<>();

        String[] values = line.split(",");

        // check đủ cột: department_name (tối thiểu 1 cột)
        if (values.length < 1 || values[0].trim().isEmpty()) {
            return line + ",Thiếu cột dữ liệu (cần ít nhất 1 cột: department_name)";
        }

        // 1. Validate department_name
        String departmentName = values[0].trim();
        if (departmentName.length() < 3 || departmentName.length() > 50) {
            errors.add("Department name phải từ 3 đến 50 kí tự");
        } else {
            // check trùng tên trong DB
            if (repository.checkExistByName(departmentName)) {
                errors.add("Department name đã tồn tại trong DB");
            }
            // check trùng tên trong danh sách đang import
            for (Department dept : validEntities) {
                if (dept.getDepartmentName().equalsIgnoreCase(departmentName)) {
                    errors.add("Department name bị trùng trong file CSV");
                    break;
                }
            }
        }

        if (errors.isEmpty()) {
            Department dept = new Department();
            dept.setDepartmentName(departmentName);
            validEntities.add(dept);
            return null;
        } else {
            String error = String.join(", ", errors);
            line = line + "," + error;
        }
        return line;
    }
}

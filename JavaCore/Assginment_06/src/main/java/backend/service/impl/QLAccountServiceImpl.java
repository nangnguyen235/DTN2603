package backend.service.impl;

import backend.repository.IGenericRepository;
import backend.repository.IQLAccountRepository;
import backend.repository.impl.QLAccountRepositoryImpl;
import backend.service.IQLAccountService;
import common.StringCommon;
import entity.Account;

import java.util.ArrayList;
import java.util.List;

public class QLAccountServiceImpl extends AbstractGenericService<Account, Integer> implements IQLAccountService {
    private IQLAccountRepository repository;

    public QLAccountServiceImpl() {
        repository = new QLAccountRepositoryImpl();
    }

    @Override
    protected IGenericRepository<Account, Integer> getRepository() {
        return repository;
    }

    // ==================== Account-specific methods ====================

    @Override
    public boolean themAccount(String email, String username, String fullName, int departmentId, int positionId) {
        return repository.themAccount(email, username, fullName, departmentId, positionId);
    }

    @Override
    public boolean xoaAccountTheoId(int id) {
        return repository.xoaAccountTheoId(id);
    }

    @Override
    public boolean suaUsernameTheoId(int id, String newUsername) {
        return repository.suaUsernameTheoId(id, newUsername);
    }

    @Override
    public boolean checkExistByEmail(String email) {
        return repository.checkExistByEmail(email);
    }

    // ==================== CSV Import (implement ICsvImportable) ====================

    /**
     * Validate và parse 1 dòng CSV thành Account
     * Format CSV: email,username,full_name,department_id,position_id
     */
    @Override
    public String validateAndParse(String line, List<Account> validEntities) {
        List<String> errors = new ArrayList<>();

        String[] values = line.split(",");

        // check đủ cột: email, username, full_name, department_id, position_id
        if (values.length < 5) {
            return line + ",Thiếu cột dữ liệu (cần 5 cột: email, username, full_name, department_id, position_id)";
        }

        // 1. Validate email
        String email = values[0].trim();
        if (email.isEmpty()) {
            errors.add("Email không được để trống");
        } else if (!email.matches(StringCommon.EMAIL_REGEX)) {
            errors.add("Email không hợp lệ");
        } else {
            if (repository.checkExistByEmail(email)) {
                errors.add("Email đã tồn tại trong DB");
            }
        }

        // 2. Validate username
        String username = values[1].trim();
        if (username.length() < 3 || username.length() > 50) {
            errors.add("Username phải từ 3 đến 50 kí tự");
        }

        // 3. Validate full_name
        String fullName = values[2].trim();
        if (fullName.length() < 3 || fullName.length() > 50) {
            errors.add("Full name phải từ 3 đến 50 kí tự");
        }

        // 4. Validate department_id
        String deptIdStr = values[3].trim();
        int departmentId = 0;
        if (!deptIdStr.matches(StringCommon.NUMBER_REGEX)) {
            errors.add("Department ID phải là số");
        } else {
            departmentId = Integer.parseInt(deptIdStr);
            if (departmentId <= 0) {
                errors.add("Department ID phải > 0");
            }
        }

        // 5. Validate position_id
        String posIdStr = values[4].trim();
        int positionId = 0;
        if (!posIdStr.matches(StringCommon.NUMBER_REGEX)) {
            errors.add("Position ID phải là số");
        } else {
            positionId = Integer.parseInt(posIdStr);
            if (positionId <= 0) {
                errors.add("Position ID phải > 0");
            }
        }

        if (errors.isEmpty()) {
            // Tạo Account object để lưu vào DB thông qua generic create()
            // Sử dụng themAccount trực tiếp
            repository.themAccount(email, username, fullName, departmentId, positionId);
            // ko add vào validEntities vì đã lưu trực tiếp
            return null;
        } else {
            String error = String.join(", ", errors);
            line = line + "," + error;
        }
        return line;
    }

    /**
     * Override importCSV để đếm chính xác số record thêm thành công
     * (vì Account dùng themAccount trực tiếp trong validateAndParse)
     */
    @Override
    public String importCSV(String url) {
        // Dùng logic generic từ AbstractGenericService
        return super.importCSV(url);
    }
}

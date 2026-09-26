package backend.service.impl;

import backend.repository.IQLAccountRepository;
import backend.repository.impl.QLAccountRepositoryImpl;
import backend.service.IQLAccountService;
import common.StringCommon;
import entity.Account;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class QLAccountServiceImpl implements IQLAccountService {
    private IQLAccountRepository repository;

    public QLAccountServiceImpl() {
        repository = new QLAccountRepositoryImpl();
    }

    @Override
    public List<Account> getAll() {
        List<Account> accounts = repository.getAll();
        return accounts;
    }

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

    @Override
    public String importCSV(String url) {
        // check file có đúng định dạng ko (có đuôi là .csv ko)
        if (!url.endsWith(".csv")) {
            return "File không đúng định dạng!";
        }
        // check xem file có tồn tại ko
        File file = new File(url);
        if (!file.exists()) {
            return "File không tồn tại!";
        }

        List<String[]> validRows = new ArrayList<>(); // lưu các row hợp lệ
        List<String> listErrors = new ArrayList<>(); // tập hợp các row và message lỗi
        String header = "";

        // đọc file -> ds các account trong file
        try (BufferedReader br = new BufferedReader(new FileReader(url))) {
            String line;
            header = br.readLine(); // đọc và bỏ qua dòng đầu tiên (header)
            while ((line = br.readLine()) != null) {
                String message = this.validationAccount(line, validRows);
                if (Objects.nonNull(message)) {
                    listErrors.add(message);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        // lưu ds các account ko gặp lỗi vào DB
        for (String[] row : validRows) {
            String email = row[0];
            String username = row[1];
            String fullName = row[2];
            int departmentId = Integer.parseInt(row[3]);
            int positionId = Integer.parseInt(row[4]);
            repository.themAccount(email, username, fullName, departmentId, positionId);
        }

        // xuất ra file lỗi ra file csv
        if (!listErrors.isEmpty()) {
            // lưu file lỗi cùng thư mục với file import
            String errorFilePath = file.getParent() + File.separator + "import_errors.csv";
            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(errorFilePath));
                bw.write(header + ",error_messages");
                bw.newLine();
                for (String error : listErrors) {
                    bw.write(error);
                    bw.newLine();
                }
                bw.flush();
                bw.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            return "Đã xuất ra file lỗi: " + errorFilePath;
        }
        return "Import thành công! Đã thêm " + validRows.size() + " account.";
    }

    // validate từng dòng CSV: email,username,full_name,department_id,position_id
    public String validationAccount(String line, List<String[]> validRows) {
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
            validRows.add(new String[]{email, username, fullName, deptIdStr, posIdStr});
            return null;
        } else {
            String error = String.join(", ", errors);
            line = line + "," + error;
        }
        return line;
    }
}

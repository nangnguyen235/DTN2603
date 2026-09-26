package frontend;

import backend.controller.QLAccountController;
import backend.controller.QLDepartmentController;
import backend.controller.QLPositionController;
import entity.Account;
import entity.Department;
import entity.Position;
import entity.enums.PositionName;
import utils.ScannerUtils;

import java.util.List;
import java.util.Scanner;

public class Function {// chứa các chức năng project có
    private Scanner scanner;
    private QLDepartmentController departmentController;
    private QLPositionController positionController;
    private QLAccountController accountController;

    public Function() {
        scanner = new Scanner(System.in);
        departmentController = new QLDepartmentController();
        positionController = new QLPositionController();
        accountController = new QLAccountController();
    }

    // ==================== DEPARTMENT ====================

    public void hienThiDepartment() {
        System.out.println("==== Hiển thị toàn bộ department ====");
        List<Department> list = departmentController.getAll();
        showDepartment(list);
    }

    public void themDepartment() {
        System.out.println("Nhập tên department (3-50 kí tự):");
        String departmentName = ScannerUtils.checkLength(3, 50);

        boolean check = departmentController.themDepartment(departmentName);
        if (check) {
            System.out.println("Thêm department thành công!");
        } else {
            System.out.println("Thêm department không thành công!");
        }
    }

    public void xoaDepartment() {
        System.out.println("Nhập ID department muốn xóa:");
        int id = ScannerUtils.inputInt(false, 1, null);

        boolean check = departmentController.xoaDepartmentTheoId(id);
        if (check) {
            System.out.println("Xóa department thành công!");
        } else {
            System.out.println("Không tìm thấy department có ID = " + id);
        }
    }

    public void suaDepartment() {
        System.out.println("Nhập ID department muốn sửa:");
        int id = ScannerUtils.inputInt(false, 1, null);

        System.out.println("Nhập tên department mới (3-50 kí tự):");
        String newName = ScannerUtils.checkLength(3, 50);

        boolean check = departmentController.suaDepartmentNameTheoId(id, newName);
        if (check) {
            System.out.println("Sửa department thành công!");
        } else {
            System.out.println("Không tìm thấy department có ID = " + id);
        }
    }

    public void showDepartment(List<Department> list) {
        System.out.println("+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |%n", "ID", "Department Name");
        System.out.println("+--------------------+--------------------+");
        if (list.size() > 0) {
            for (Department dept : list) {
                System.out.println(dept);
            }
        } else {
            System.out.printf("|%39s |%n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+");
    }

    // ==================== POSITION ====================

    public void hienThiPosition() {
        System.out.println("==== Hiển thị toàn bộ position ====");
        List<Position> list = positionController.getAll();
        showPosition(list);
    }

    public void themPosition() {
        System.out.println("Chọn position name:");
        PositionName[] values = PositionName.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        System.out.println("Nhập số tương ứng:");
        int choice = ScannerUtils.inputInt(false, 1, values.length);
        PositionName selectedName = values[choice - 1];

        boolean check = positionController.themPosition(selectedName.name());
        if (check) {
            System.out.println("Thêm position thành công!");
        } else {
            System.out.println("Thêm position không thành công!");
        }
    }

    public void xoaPosition() {
        System.out.println("Nhập ID position muốn xóa:");
        int id = ScannerUtils.inputInt(false, 1, null);

        boolean check = positionController.xoaPositionTheoId(id);
        if (check) {
            System.out.println("Xóa position thành công!");
        } else {
            System.out.println("Không tìm thấy position có ID = " + id);
        }
    }

    public void suaPosition() {
        System.out.println("Nhập ID position muốn sửa:");
        int id = ScannerUtils.inputInt(false, 1, null);

        System.out.println("Chọn position name mới:");
        PositionName[] values = PositionName.values();
        for (int i = 0; i < values.length; i++) {
            System.out.println((i + 1) + ". " + values[i]);
        }
        System.out.println("Nhập số tương ứng:");
        int choice = ScannerUtils.inputInt(false, 1, values.length);
        PositionName selectedName = values[choice - 1];

        boolean check = positionController.suaPositionNameTheoId(id, selectedName.name());
        if (check) {
            System.out.println("Sửa position thành công!");
        } else {
            System.out.println("Không tìm thấy position có ID = " + id);
        }
    }

    public void showPosition(List<Position> list) {
        System.out.println("+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |%n", "ID", "Position Name");
        System.out.println("+--------------------+--------------------+");
        if (list.size() > 0) {
            for (Position pos : list) {
                System.out.println(pos);
            }
        } else {
            System.out.printf("|%39s |%n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+");
    }

    // ==================== ACCOUNT ====================

    public void hienThiAccount() {
        System.out.println("==== Hiển thị toàn bộ account ====");
        List<Account> list = accountController.getAll();
        showAccount(list);
    }

    public void themAccount() {
        System.out.println("Nhập email:");
        String email;
        while (true) {
            email = ScannerUtils.inputEmail();
            // check xem email đã tồn tại trong DB chưa
            boolean checkExist = accountController.checkExistByEmail(email);
            if (checkExist) {
                System.err.println("Email này đã tồn tại. Nhập lại!");
                continue;
            }
            break;
        }

        System.out.println("Nhập username (3-50 kí tự):");
        String username = ScannerUtils.checkLength(3, 50);

        System.out.println("Nhập full name (3-50 kí tự):");
        String fullName = ScannerUtils.checkLength(3, 50);

        System.out.println("Nhập department_id:");
        int departmentId = ScannerUtils.inputInt(false, 1, null);

        System.out.println("Nhập position_id:");
        int positionId = ScannerUtils.inputInt(false, 1, null);

        boolean check = accountController.themAccount(email, username, fullName, departmentId, positionId);
        if (check) {
            System.out.println("Thêm account thành công!");
        } else {
            System.out.println("Thêm account không thành công!");
        }
    }

    public void xoaAccount() {
        System.out.println("Nhập ID account muốn xóa:");
        int id = ScannerUtils.inputInt(false, 1, null);

        boolean check = accountController.xoaAccountTheoId(id);
        if (check) {
            System.out.println("Xóa account thành công!");
        } else {
            System.out.println("Không tìm thấy account có ID = " + id);
        }
    }

    public void suaAccount() {
        System.out.println("Nhập ID account muốn sửa:");
        int id = ScannerUtils.inputInt(false, 1, null);

        System.out.println("Nhập username mới (3-50 kí tự):");
        String newUsername = ScannerUtils.checkLength(3, 50);

        boolean check = accountController.suaUsernameTheoId(id, newUsername);
        if (check) {
            System.out.println("Sửa username thành công!");
        } else {
            System.out.println("Không tìm thấy account có ID = " + id);
        }
    }

    public void showAccount(List<Account> list) {
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
        System.out.printf("|%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |%19s |%n",
                "ID", "Email", "Username", "Full Name", "Department ID", "Department Name", "Position ID", "Position Name", "Create Date");
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");

        if (list.size() > 0) {
            for (Account acc : list) {
                System.out.println(acc);
            }
        } else {
            System.out.printf("|%179s |%n", "Không có thông tin");
        }
        System.out.println("+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+--------------------+");
    }

    // ==================== IMPORT CSV ====================

    private void importCSV() {
        // đưa file csv vào thông qua 1 đường dẫn
        System.out.println("Nhập vào đường dẫn file csv muốn import: ");
        String url = scanner.nextLine();
        // đưa đường dẫn cho controller
        String message = accountController.importCSV(url);
        System.out.println(message);
    }

    // ==================== MENU ====================

    public void menu() {
        while (true) {
            System.out.println("========================================");
            System.out.println("==== Mời bạn chọn module ====");
            System.out.println("========================================");
            System.out.println("1. Quản lý Department.");
            System.out.println("2. Quản lý Position.");
            System.out.println("3. Quản lý Account.");
            System.out.println("4. Import Account từ file CSV.");
            System.out.println("5. Thoát khỏi chương trình.");
            System.out.print("Nhập lựa chọn: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    menuDepartment();
                    break;
                case "2":
                    menuPosition();
                    break;
                case "3":
                    menuAccount();
                    break;
                case "4":
                    this.importCSV();
                    break;
                case "5":
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("Nhập sai, nhập lại!");
            }
        }
    }

    private void menuDepartment() {
        while (true) {
            System.out.println("========================================");
            System.out.println("==== Quản lý Department ====");
            System.out.println("========================================");
            System.out.println("1. Hiển thị toàn bộ department.");
            System.out.println("2. Thêm department.");
            System.out.println("3. Xóa department theo ID.");
            System.out.println("4. Sửa department_name theo ID.");
            System.out.println("5. Quay lại menu chính.");
            System.out.print("Nhập lựa chọn: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    this.hienThiDepartment();
                    break;
                case "2":
                    this.themDepartment();
                    break;
                case "3":
                    this.xoaDepartment();
                    break;
                case "4":
                    this.suaDepartment();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Nhập sai, nhập lại!");
            }
        }
    }

    private void menuPosition() {
        while (true) {
            System.out.println("========================================");
            System.out.println("==== Quản lý Position ====");
            System.out.println("========================================");
            System.out.println("1. Hiển thị toàn bộ position.");
            System.out.println("2. Thêm position.");
            System.out.println("3. Xóa position theo ID.");
            System.out.println("4. Sửa position_name theo ID.");
            System.out.println("5. Quay lại menu chính.");
            System.out.print("Nhập lựa chọn: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    this.hienThiPosition();
                    break;
                case "2":
                    this.themPosition();
                    break;
                case "3":
                    this.xoaPosition();
                    break;
                case "4":
                    this.suaPosition();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Nhập sai, nhập lại!");
            }
        }
    }

    private void menuAccount() {
        while (true) {
            System.out.println("========================================");
            System.out.println("==== Quản lý Account ====");
            System.out.println("========================================");
            System.out.println("1. Hiển thị toàn bộ account.");
            System.out.println("2. Thêm account.");
            System.out.println("3. Xóa account theo ID.");
            System.out.println("4. Sửa username theo ID.");
            System.out.println("5. Quay lại menu chính.");
            System.out.print("Nhập lựa chọn: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    this.hienThiAccount();
                    break;
                case "2":
                    this.themAccount();
                    break;
                case "3":
                    this.xoaAccount();
                    break;
                case "4":
                    this.suaAccount();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Nhập sai, nhập lại!");
            }
        }
    }
}

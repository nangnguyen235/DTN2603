package frontend;

import backend.IQLAccount;
import backend.QLAccount;
import backend.IQLDepartment;
import backend.QLDepartment;
import backend.IQLPosition;
import backend.QLPosition;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        Main m = new Main();
        m.menu();
    }

    public void menu() {
        IQLAccount qlAccount = new QLAccount();
        IQLDepartment qlDepartment = new QLDepartment();
        IQLPosition qlPosition = new QLPosition();

        while (true) {
            System.out.println("========================================");
            System.out.println("==== Mời bạn chọn module ====");
            System.out.println("========================================");
            System.out.println("1. Quản lý Department.");
            System.out.println("2. Quản lý Position.");
            System.out.println("3. Quản lý Account.");
            System.out.println("4. Thoát khỏi chương trình.");
            System.out.print("Nhập lựa chọn: ");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    menuDepartment(qlDepartment);
                    break;
                case "2":
                    menuPosition(qlPosition);
                    break;
                case "3":
                    menuAccount(qlAccount);
                    break;
                case "4":
                    System.out.println("Thoát chương trình. Tạm biệt!");
                    System.exit(0);
                default:
                    System.out.println("Nhập sai, nhập lại!");
            }
        }
    }

    private void menuDepartment(IQLDepartment qlDepartment) {
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
                    qlDepartment.hienThiThongTin();
                    break;
                case "2":
                    qlDepartment.themDepartment();
                    break;
                case "3":
                    qlDepartment.xoaDepartmentTheoId();
                    break;
                case "4":
                    qlDepartment.suaDepartmentNameTheoId();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Nhập sai, nhập lại!");
            }
        }
    }

    private void menuPosition(IQLPosition qlPosition) {
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
                    qlPosition.hienThiThongTin();
                    break;
                case "2":
                    qlPosition.themPosition();
                    break;
                case "3":
                    qlPosition.xoaPositionTheoId();
                    break;
                case "4":
                    qlPosition.suaPositionNameTheoId();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Nhập sai, nhập lại!");
            }
        }
    }

    private void menuAccount(IQLAccount qlAccount) {
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
                    qlAccount.hienThiThongTin();
                    break;
                case "2":
                    qlAccount.themAccount();
                    break;
                case "3":
                    qlAccount.xoaAccountTheoId();
                    break;
                case "4":
                    qlAccount.suaUsernameTheoId();
                    break;
                case "5":
                    return;
                default:
                    System.out.println("Nhập sai, nhập lại!");
            }
        }
    }
}
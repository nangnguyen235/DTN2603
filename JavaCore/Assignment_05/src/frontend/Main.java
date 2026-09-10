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
        IQLAccount qlaccount = new QLAccount();
        IQLDepartment qldepartment = new QLDepartment();
        IQLPosition qlposition = new QLPosition();
        while (true) {
            System.out.println("==== Mời bạn chọn chức năng ====");
            System.out.println("1. Hiện thị thông tin về Account.");
            System.out.println("2. Hiện thị thông tin về Department.");
            System.out.println("3. Hiện thị thông tin về Position.");
            System.out.println("4. Thoát khỏi chương trình.");
            String choice = scanner.nextLine();
            switch (choice) {
                case "1":
                    qlaccount.hienThiThongTin();
                    break;
                case "2":
                    qldepartment.hienThiThongTin();
                    break;
                case "3":
                    qlposition.hienThiThongTin();
                    break;
                case "4":
                    System.exit(0);
                default:
                    System.out.println("Chọn sai! chọn lại!");
            }
        }
    }
}
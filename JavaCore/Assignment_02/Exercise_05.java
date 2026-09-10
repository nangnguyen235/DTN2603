import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.Scanner;

public class Exercise5 {
    static Scanner scanner = new Scanner(System.in);
    static List<Account> accountList = new ArrayList<>();
    static List<Department> departmentList = new ArrayList<>();
    static List<Group> groupList = new ArrayList<>();

    public static void main(String[] args) {
        initSampleData();

        // Chạy flow tổng hợp cho Questions 8, 9, 10, 11
        runMenuProgram();
    }

    private static void initSampleData() {
        Department dep1 = new Department(1, "Sale");
        Department dep2 = new Department(2, "Marketing");
        departmentList.addAll(Arrays.asList(dep1, dep2));

        Group group1 = new Group(1, "Java Fresher");
        Group group2 = new Group(2, "C# Fresher");
        groupList.addAll(Arrays.asList(group1, group2));

        Account acc1 = new Account(1, "vana@gmail.com", "vana", "Nguyễn Văn A");
        Account acc2 = new Account(2, "vanb@gmail.com", "vanb", "Nguyễn Văn B");
        accountList.addAll(Arrays.asList(acc1, acc2));
    }

    // Question 1
    public static void question1() {
        System.out.println("Nhập vào 3 số nguyên:");
        int a = scanner.nextInt();
        int b = scanner.nextInt();
        int c = scanner.nextInt();
        scanner.nextLine();
        System.out.printf("3 số nguyên vừa nhập: %d, %d, %d%n", a, b, c);
    }

    // Question 2
    public static void question2() {
        System.out.println("Nhập vào 2 số thực:");
        double a = scanner.nextDouble();
        double b = scanner.nextDouble();
        scanner.nextLine();
        System.out.printf("2 số thực vừa nhập: %f, %f%n", a, b);
    }

    // Question 3
    public static void question3() {
        System.out.print("Nhập họ và tên: ");
        String name = scanner.nextLine();
        System.out.println("Họ và tên: " + name);
    }

    // Question 4
    public static void question4() {
        System.out.print("Nhập ngày sinh (dd/MM/yyyy): ");
        String dateStr = scanner.nextLine();
        LocalDate dob = LocalDate.parse(dateStr, DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        System.out.println("Ngày sinh đã nhập: " + dob);
    }

    // Question 5: Tạo Account
    public static Account question5CreateAccount() {
        Account acc = new Account();
        System.out.print("Nhập ID Account: ");
        acc.id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập Email: ");
        acc.email = scanner.nextLine();

        System.out.print("Nhập Username: ");
        acc.userName = scanner.nextLine();

        System.out.print("Nhập Fullname: ");
        acc.fullName = scanner.nextLine();

        System.out.println("Chọn Position (1.Dev, 2.Test, 3.ScrumMaster, 4.PM): ");
        int posOpt = scanner.nextInt();
        scanner.nextLine();

        Position position = new Position();
        position.id = posOpt;
        switch (posOpt) {
            case 1:
                position.name = PositionName.DEV;
                break;
            case 2:
                position.name = PositionName.TEST;
                break;
            case 3:
                position.name = PositionName.SCRUM_MASTER;
                break;
            case 4:
                position.name = PositionName.PM;
                break;
            default:
                position.name = PositionName.DEV;
                break;
        }
        acc.position = position;
        accountList.add(acc);
        System.out.println("Tạo Account thành công!");
        return acc;
    }

    // Question 6: Tạo QLDepartment.java
    public static Department question6CreateDepartment() {
        Department dep = new Department();
        System.out.print("Nhập ID QLDepartment.java: ");
        dep.id = scanner.nextInt();
        scanner.nextLine();

        System.out.print("Nhập Tên QLDepartment.java: ");
        dep.name = scanner.nextLine();

        departmentList.add(dep);
        System.out.println("Tạo QLDepartment.java thành công!");
        return dep;
    }

    // Question 7: Nhập số chẵn
    public static void question7() {
        while (true) {
            System.out.print("Nhập vào 1 số chẵn: ");
            int num = scanner.nextInt();
            scanner.nextLine();
            if (num % 2 == 0) {
                System.out.println("Số chẵn bạn vừa nhập là: " + num);
                break;
            } else {
                System.out.println("Số vừa nhập không phải số chẵn. Vui lòng nhập lại!");
            }
        }
    }

    // Question 9: Thêm Group vào Account theo username và groupName
    public static void question9AddGroupToAccount() {
        System.out.println("--- Danh sách Username hiện có ---");
        for (Account a : accountList) {
            System.out.println("- " + a.userName);
        }

        System.out.print("Nhập vào Username của account: ");
        String uName = scanner.nextLine();

        System.out.println("--- Danh sách Group hiện có ---");
        for (Group g : groupList) {
            System.out.println("- " + g.name);
        }

        System.out.print("Nhập vào Tên Group muốn thêm: ");
        String gName = scanner.nextLine();

        Account selectedAcc = null;
        for (Account a : accountList) {
            if (a.userName.equalsIgnoreCase(uName)) {
                selectedAcc = a;
                break;
            }
        }

        Group selectedGroup = null;
        for (Group g : groupList) {
            if (g.name.equalsIgnoreCase(gName)) {
                selectedGroup = g;
                break;
            }
        }

        if (selectedAcc != null && selectedGroup != null) {
            attachGroupToAccount(selectedAcc, selectedGroup);
            System.out.printf("Đã thêm account '%s' vào group '%s'!%n", selectedAcc.userName, selectedGroup.name);
        } else {
            System.out.println("Không tìm thấy Account hoặc Group tương ứng.");
        }
    }

    // Question 11: Thêm Account vào 1 Group ngẫu nhiên
    public static void question11AddAccountToRandomGroup() {
        System.out.println("--- Danh sách Username hiện có ---");
        for (Account a : accountList) {
            System.out.println("- " + a.userName);
        }

        System.out.print("Nhập vào Username của account: ");
        String uName = scanner.nextLine();

        Account selectedAcc = null;
        for (Account a : accountList) {
            if (a.userName.equalsIgnoreCase(uName)) {
                selectedAcc = a;
                break;
            }
        }

        if (selectedAcc == null) {
            System.out.println("Không tìm thấy username này!");
            return;
        }

        if (groupList.isEmpty()) {
            System.out.println("Hiện chưa có nhóm nào trong hệ thống.");
            return;
        }

        Random random = new Random();
        Group randGroup = groupList.get(random.nextInt(groupList.size()));
        attachGroupToAccount(selectedAcc, randGroup);
        System.out.printf("Đã thêm ngẫu nhiên account '%s' vào group '%s'!%n", selectedAcc.userName, randGroup.name);
    }

    private static void attachGroupToAccount(Account acc, Group grp) {
        if (acc.groups == null) {
            acc.groups = new Group[]{grp};
        } else {
            Group[] newGroups = Arrays.copyOf(acc.groups, acc.groups.length + 1);
            newGroups[newGroups.length - 1] = grp;
            acc.groups = newGroups;
        }
    }

    // Tổng hợp Question 8 + 10 + 11 (Menu điều hướng)
    public static void runMenuProgram() {
        while (true) {
            System.out.println("\n===== MENU CHỨC NĂNG =====");
            System.out.println("1. Tạo Account");
            System.out.println("2. Tạo QLDepartment.java");
            System.out.println("3. Thêm Group vào Account");
            System.out.println("4. Thêm Account vào Group ngẫu nhiên");
            System.out.print("Mời bạn nhập vào chức năng muốn sử dụng: ");

            int choice;
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                scanner.nextLine();
            } else {
                scanner.nextLine();
                System.out.println("Mời bạn nhập lại!");
                continue;
            }

            switch (choice) {
                case 1:
                    question5CreateAccount();
                    break;
                case 2:
                    question6CreateDepartment();
                    break;
                case 3:
                    question9AddGroupToAccount();
                    break;
                case 4:
                    question11AddAccountToRandomGroup();
                    break;
                default:
                    System.out.println("Mời bạn nhập lại!");
                    continue;
            }

            // Question 10 Step 3
            System.out.print("\nBạn có muốn thực hiện chức năng khác không? (Có / Không): ");
            String continueChoice = scanner.nextLine().trim();
            if (continueChoice.equalsIgnoreCase("Không") || continueChoice.equalsIgnoreCase("Khong") || continueChoice.equalsIgnoreCase("K")) {
                System.out.println("Cảm ơn bạn đã sử dụng chương trình!");
                return;
            }
        }
    }
}
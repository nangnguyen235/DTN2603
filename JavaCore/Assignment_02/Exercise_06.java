public class Exercise6 {

    // Question 1: In các số chẵn nguyên dương nhỏ hơn 10 (2, 4, 6, 8)
    public static void printPositiveEvenNumbersLessThanTen() {
        for (int i = 2; i < 10; i += 2) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    // Question 2: In thông tin các account
    public static void printAccounts(Account[] accounts) {
        if (accounts == null || accounts.length == 0) {
            System.out.println("Danh sách account rỗng.");
            return;
        }
        for (Account a : accounts) {
            String depName = (a.department != null) ? a.department.name : "Chưa có";
            String posName = (a.position != null && a.position.name != null) ? a.position.name.name() : "Chưa có";
            System.out.println("Id: " + a.id + " | FullName: " + a.fullName + " | Email: " + a.email + " | QLDepartment.java: " + depName + " | Position: " + posName);
        }
    }

    // Question 3: In các số nguyên dương nhỏ hơn 10 (1 -> 9)
    public static void printPositiveNumbersLessThanTen() {
        for (int i = 1; i < 10; i++) {
            System.out.print(i + " ");
        }
        System.out.println();
    }

    public static void main(String[] args) {
        System.out.println("--- Question 1 ---");
        printPositiveEvenNumbersLessThanTen();

        System.out.println("\n--- Question 2 ---");
        Account acc1 = new Account(1, "vana@gmail.com", "vana", "Nguyễn Văn A");
        acc1.department = new Department(1, "Sale");
        Account acc2 = new Account(2, "vanb@gmail.com", "vanb", "Nguyễn Văn B");
        acc2.department = new Department(2, "Marketing");
        Account[] accounts = {acc1, acc2};
        printAccounts(accounts);

        System.out.println("\n--- Question 3 ---");
        printPositiveNumbersLessThanTen();
    }
}
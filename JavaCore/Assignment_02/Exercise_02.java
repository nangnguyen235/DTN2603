import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class Exercise2 {
    public static void main(String[] args) {
        // Question 1
        int num1 = 5;
        System.out.printf("%d%n", num1);

        // Question 2
        int num2 = 100000000;
        System.out.printf(Locale.US, "%,d%n", num2);

        // Question 3
        float num3 = 5.567098f;
        System.out.printf(Locale.US, "%.4f%n", num3);

        // Question 4
        String fullName = "Nguyễn Văn A";
        System.out.printf("Tên tôi là \"%s\" và tôi đang độc thân.%n", fullName);

        // Question 5
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd/MM/yyyy HH'h':mm'p':ss's'");
        System.out.println(now.format(dtf));

        // Question 6: Format Table Database
        Account acc1 = new Account(1, "NguyenVanA@gmail.com", "vana", "Nguyễn Văn A");
        acc1.department = new Department(1, "Sale");
        Account acc2 = new Account(2, "NguyenVanB@gmail.com", "vanb", "Nguyễn Văn B");
        acc2.department = new Department(2, "Marketing");
        Account[] accounts = {acc1, acc2};

        System.out.println("+----+---------------------------+---------------------+---------------------+");
        System.out.printf("| %-2s | %-25s | %-19s | %-19s |%n", "ID", "Email", "Full Name", "QLDepartment.java");
        System.out.println("+----+---------------------------+---------------------+---------------------+");
        for (Account a : accounts) {
            String depName = (a.department != null) ? a.department.name : "";
            System.out.printf("| %-2d | %-25s | %-19s | %-19s |%n", a.id, a.email, a.fullName, depName);
        }
        System.out.println("+----+---------------------------+---------------------+---------------------+");
    }
}
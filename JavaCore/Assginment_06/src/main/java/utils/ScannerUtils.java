package utils;

import common.StringCommon;

import java.util.Objects;
import java.util.Scanner;

public class ScannerUtils {
    private static Scanner scanner = new Scanner(System.in);

    public static String checkLength(int min, int max) {
        while (true) {
            String text = scanner.nextLine();
            if (text.trim().length() < min || text.trim().length() > max) {
                System.err.println(String.format("Vui lòng nhập từ %d đến %d kí tự!\n", min, max));
                continue;
            }
            return text;
        }
    }

    public static int inputInt(boolean isNegative, Integer min, Integer max) {
        int input = 0;
        while (true) {
            if (!scanner.hasNextInt()) {
                scanner.nextLine();
                System.err.println("Vui lòng nhập số: ");
                continue;
            }
            input = scanner.nextInt();
            scanner.nextLine();
            // ko chứa số âm
            if (!isNegative & input < 0) {
                System.err.println("Vui lòng nhập số > 0: ");
                continue;
            }
            if (Objects.nonNull(min) && input < min) {
                System.err.println("Vui lòng nhập số lớn hơn " + min);
                continue;
            }
            if (Objects.nonNull(max) && input > max) {
                System.err.println("Vui lòng nhập số nhỏ hơn " + max);
                continue;
            }
            return input;
        }
    }

    public static String inputDate() {
        while (true) {
            String date = scanner.nextLine();

            if (!date.trim().matches(StringCommon.YYYY_MM_DD_REGEX)) {
                System.err.println("Nhập ngày ko hợp lệ (yyyy-MM-dd)");
                continue;
            }
            return date;
        }
    }

    public static String inputEmail() {
        while (true) {
            String email = scanner.nextLine();

            if (!email.trim().matches(StringCommon.EMAIL_REGEX)) {
                System.err.println("Email ko hợp lệ");
                continue;
            }
            return email;
        }
    }
}

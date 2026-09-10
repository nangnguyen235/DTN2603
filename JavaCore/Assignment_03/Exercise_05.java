import java.util.Arrays;
import java.util.Objects;

public class Exercise_05 {

    static class Department implements Comparable<Department> {
        int id;
        String name;

        public Department() {
        }

        public Department(int id, String name) {
            this.id = id;
            this.name = name;
        }

        @Override
        public String toString() {
            return "QLDepartment.java{" +
                    "id=" + id +
                    ", name='" + name + '\'' +
                    '}';
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            Department that = (Department) o;
            return Objects.equals(name, that.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name);
        }

        @Override
        public int compareTo(Department o) {
            if (this.name == null && o.name == null) return 0;
            if (this.name == null) return -1;
            if (o.name == null) return 1;
            return this.name.compareToIgnoreCase(o.name);
        }
    }

    public static void main(String[] args) {
        System.out.println("=== EXERCISE 5: OBJECT'S METHOD ===");

        // Khởi tạo danh sách phòng ban làm dữ liệu mẫu
        Department dep1 = new Department(1, "Sale");
        Department dep2 = new Department(2, "Marketing");
        Department dep3 = new Department(3, "Accounting");
        Department dep4 = new Department(4, "Boss of director");
        Department dep5 = new Department(5, "Waiting room");

        Department[] departments = {dep1, dep2, dep3, dep4, dep5};

        // Question 1
        System.out.println("\n--- Question 1 ---");
        System.out.println("Thông tin phòng ban thứ 1 (dùng toString()):");
        System.out.println(dep1.toString());

        // Question 2
        System.out.println("\n--- Question 2 ---");
        System.out.println("Thông tin tất cả các phòng ban (dùng toString()):");
        for (Department d : departments) {
            System.out.println(d);
        }

        // Question 3
        System.out.println("\n--- Question 3 ---");
        System.out.println("Địa chỉ/Identity Hash Code của phòng ban thứ 1:");
        System.out.println("Address (Hex): 0x" + Integer.toHexString(System.identityHashCode(dep1)));

        // Question 4
        System.out.println("\n--- Question 4 ---");
        boolean isPhongA = "Phòng A".equalsIgnoreCase(dep1.name);
        System.out.println("Phòng ban thứ 1 tên là '" + dep1.name + "'. Có phải 'Phòng A' không? " + isPhongA);

        // Question 5
        System.out.println("\n--- Question 5 ---");
        boolean isEqual = dep1.equals(dep2);
        System.out.println("Phòng ban 1 ('" + dep1.name + "') và Phòng ban 2 ('" + dep2.name + "') có bằng nhau không? " + isEqual);

        // Question 6
        System.out.println("\n--- Question 6 ---");
        Department[] depsQ6 = {
                new Department(1, "Accounting"),
                new Department(2, "Boss of director"),
                new Department(3, "Marketing"),
                new Department(4, "Sale"),
                new Department(5, "Waiting room")
        };
        Arrays.sort(depsQ6);
        System.out.println("Danh sách 5 phòng ban sắp xếp tăng dần theo tên (vần ABCD):");
        for (Department d : depsQ6) {
            System.out.println(d.name);
        }

        // Question 7
        System.out.println("\n--- Question 7 ---");
        Department[] depsQ7 = {
                new Department(1, "Accounting"),
                new Department(2, "Boss of director"),
                new Department(3, "Marketing"),
                new Department(4, "waiting room"),
                new Department(5, "Sale")
        };
        // Sắp xếp phân biệt hoa thường / theo tên
        Arrays.sort(depsQ7, (d1, d2) -> d1.name.compareTo(d2.name));
        System.out.println("Danh sách phòng ban/học sinh sắp xếp theo tên (compareTo có phân biệt chữ hoa/thường):");
        for (Department d : depsQ7) {
            System.out.println(d.name);
        }
    }
}

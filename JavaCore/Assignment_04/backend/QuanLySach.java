package backend;

import entity.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class QuanLySach implements IQuanLySach {
    private List<TaiLieu> dsTaiLieu = new ArrayList<>();
    private Scanner scanner;

    public QuanLySach() {
        this(new Scanner(System.in));
    }

    public QuanLySach(Scanner scanner) {
        this.scanner = scanner;
        // Dữ liệu mẫu khởi tạo ban đầu
        dsTaiLieu.add(new Sach(101, "NXB Kim Đồng", 1000, "Nam Cao", 150));
        dsTaiLieu.add(new TapChi(201, "NXB Trẻ", 500, 15, 8));
        dsTaiLieu.add(new Bao(301, "NXB Tuổi Trẻ", 2000, 25));
    }

    @Override
    public void themMoiTaiLieu() {
        System.out.println("--- THÊM MỚI TÀI LIỆU ---");
        System.out.println("Chọn loại tài liệu cần thêm: 1. Sách   2. Tạp chí   3. Báo");
        System.out.print("Lựa chọn: ");
        String loai = scanner.nextLine();

        if (!loai.equals("1") && !loai.equals("2") && !loai.equals("3")) {
            System.out.println("Loại tài liệu không hợp lệ!");
            return;
        }

        System.out.print("Nhập mã tài liệu: ");
        int maTaiLieu = Integer.parseInt(scanner.nextLine());

        // Kiểm tra mã tài liệu duy nhất
        for (TaiLieu tl : dsTaiLieu) {
            if (tl.getMaTaiLieu() == maTaiLieu) {
                System.out.println("Lỗi: Mã tài liệu " + maTaiLieu + " đã tồn tại trong hệ thống!");
                return;
            }
        }

        System.out.print("Nhập tên nhà xuất bản: ");
        String tenNxb = scanner.nextLine();

        System.out.print("Nhập số bản phát hành: ");
        int soBan = Integer.parseInt(scanner.nextLine());

        switch (loai) {
            case "1":
                System.out.print("Nhập tên tác giả: ");
                String tenTacGia = scanner.nextLine();
                System.out.print("Nhập số trang: ");
                int soTrang = Integer.parseInt(scanner.nextLine());
                dsTaiLieu.add(new Sach(maTaiLieu, tenNxb, soBan, tenTacGia, soTrang));
                System.out.println("Thêm thành công Sách!");
                break;
            case "2":
                System.out.print("Nhập số phát hành: ");
                int soPhatHanh = Integer.parseInt(scanner.nextLine());
                System.out.print("Nhập tháng phát hành: ");
                int thangPhatHanh = Integer.parseInt(scanner.nextLine());
                dsTaiLieu.add(new TapChi(maTaiLieu, tenNxb, soBan, soPhatHanh, thangPhatHanh));
                System.out.println("Thêm thành công Tạp chí!");
                break;
            case "3":
                System.out.print("Nhập ngày phát hành: ");
                int ngayPhatHanh = Integer.parseInt(scanner.nextLine());
                dsTaiLieu.add(new Bao(maTaiLieu, tenNxb, soBan, ngayPhatHanh));
                System.out.println("Thêm thành công Báo!");
                break;
        }
    }

    @Override
    public void xoaTaiLieuTheoMa() {
        System.out.print("Nhập mã tài liệu cần xoá: ");
        int maTaiLieu = Integer.parseInt(scanner.nextLine());
        boolean isRemoved = dsTaiLieu.removeIf(tl -> tl.getMaTaiLieu() == maTaiLieu);
        if (isRemoved) {
            System.out.println("Xoá thành công tài liệu có mã: " + maTaiLieu);
        } else {
            System.out.println("Không tìm thấy tài liệu có mã: " + maTaiLieu);
        }
    }

    @Override
    public void hienThiDSTaiLieu() {
        if (dsTaiLieu.isEmpty()) {
            System.out.println("Danh sách tài liệu đang trống.");
            return;
        }
        inTieuDeBang();
        for (TaiLieu tl : dsTaiLieu) {
            inThongTinTaiLieu(tl);
        }
        inDuongKe();
    }

    @Override
    public void timKiemTheoLoai() {
        System.out.println("--- TÌM KIẾM TÀI LIỆU THEO LOẠI ---");
        System.out.println("Chọn loại tài liệu muốn tìm: 1. Sách   2. Tạp chí   3. Báo");
        System.out.print("Lựa chọn: ");
        String choose = scanner.nextLine();

        boolean found = false;
        inTieuDeBang();
        for (TaiLieu tl : dsTaiLieu) {
            if (choose.equals("1") && tl instanceof Sach) {
                inThongTinTaiLieu(tl);
                found = true;
            } else if (choose.equals("2") && tl instanceof TapChi) {
                inThongTinTaiLieu(tl);
                found = true;
            } else if (choose.equals("3") && tl instanceof Bao) {
                inThongTinTaiLieu(tl);
                found = true;
            }
        }
        inDuongKe();

        if (!found) {
            System.out.println("Không tìm thấy tài liệu nào thuộc loại đã chọn.");
        }
    }

    private void inTieuDeBang() {
        inDuongKe();
        System.out.printf("|%10s|%12s|%20s|%15s|%35s|\n", "Loại TL", "Mã TL", "Tên NXB", "Số bản PH", "Thông tin riêng");
        inDuongKe();
    }

    private void inDuongKe() {
        System.out.println("+----------+------------+--------------------+---------------+-----------------------------------+");
    }

    private void inThongTinTaiLieu(TaiLieu tl) {
        if (tl instanceof Sach) {
            Sach s = (Sach) tl;
            String thongTin = "Tác giả: " + s.getTenTacGia() + ", Số trang: " + s.getSoTrang();
            System.out.printf("|%10s|%12d|%20s|%15d|%35s|\n", "Sách", s.getMaTaiLieu(), s.getten(), s.getSoBan(), thongTin);
        } else if (tl instanceof TapChi) {
            TapChi tc = (TapChi) tl;
            String thongTin = "Số PH: " + tc.getSoPhatHanh() + ", Tháng PH: " + tc.getThangPhatHanh();
            System.out.printf("|%10s|%12d|%20s|%15d|%35s|\n", "Tạp chí", tc.getMaTaiLieu(), tc.getten(), tc.getSoBan(), thongTin);
        } else if (tl instanceof Bao) {
            Bao b = (Bao) tl;
            String thongTin = "Ngày PH: " + b.getNgayPhatHanh();
            System.out.printf("|%10s|%12d|%20s|%15d|%35s|\n", "Báo", b.getMaTaiLieu(), b.getten(), b.getSoBan(), thongTin);
        }
    }
}

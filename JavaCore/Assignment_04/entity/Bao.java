package entity;

public class Bao extends TaiLieu{
    private int ngayPhatHanh;

    public Bao(){
    }

    public Bao(int maTaiLieu, String ten, int soBan, int ngayPhatHanh) {
        super(maTaiLieu, ten, soBan);
        this.ngayPhatHanh = ngayPhatHanh;
    }

    public int getNgayPhatHanh() {
        return ngayPhatHanh;
    }

    public void setNgayPhatHanh(int ngayPhatHanh) {
        this.ngayPhatHanh = ngayPhatHanh;
    }
}

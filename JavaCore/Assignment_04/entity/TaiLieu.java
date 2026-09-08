package entity;

public class TaiLieu {
    private int maTaiLieu;
    private String ten;
    private int soBan;

    public TaiLieu() {
    }

    public TaiLieu(int maTaiLieu, String ten, int soBan){
        this.maTaiLieu = maTaiLieu;
        this.soBan = soBan;
        this.ten = ten;
    }

    public int getMaTaiLieu() {
        return maTaiLieu;
    }

    public void setMaTaiLieu(int maTaiLieu) {
        this.maTaiLieu = maTaiLieu;
    }

    public String getten() {
        return ten;
    }

    public String getTen() {
        return ten;
    }

    public void setten(String ten) {
        this.ten = ten;
    }

    public void setTen(String ten) {
        this.ten = ten;
    }

    public int getSoBan() {
        return soBan;
    }

    public void setSoBan(int soBan) {
        this.soBan = soBan;
    }
}


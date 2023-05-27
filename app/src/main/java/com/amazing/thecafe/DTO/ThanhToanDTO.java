package com.amazing.thecafe.DTO;

public class ThanhToanDTO {
    String TenMon;
    int SoLuong, GiaTien;
    byte[] HinhAnh;

    public String getTenMon() {
        return TenMon;
    }

    public void setTenMon(String tenMon) {
        TenMon = tenMon;
    }

    public int getSoLuong() {
        return SoLuong;
    }

    public void setSoLuong(int soLuong) {
        SoLuong = soLuong;
    }

    public int getGiaTien() {
        return GiaTien;
    }

    public void setGiaTien(int giaTien) {
        GiaTien = giaTien;
    }

    public byte[] getHinhAnh() {
        return HinhAnh;
    }

    public void setHinhAnh(byte[] hinhAnh) {
        HinhAnh = hinhAnh;
    }
}

package com.example.news.model;

import android.graphics.Bitmap;


public class SanPham {
    private int id;
    private String tensp;
    private Bitmap hinhanh;
    private String loai;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTensp() {
        return tensp;
    }

    public void setTensp(String tensp) {
        this.tensp = tensp;
    }

    public Bitmap getHinhanh() {
        return hinhanh;
    }

    public void setHinhanh(Bitmap hinhanh) {
        this.hinhanh = hinhanh;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public SanPham(String tensp, Bitmap hinhanh, String loai) {
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.loai = loai;
    }

    public SanPham() {
    }

    public SanPham(int id, String tensp, Bitmap hinhanh, String loai) {
        this.id = id;
        this.tensp = tensp;
        this.hinhanh = hinhanh;
        this.loai = loai;
    }
}

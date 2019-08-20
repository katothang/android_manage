package com.example.news.model;

public class LoaiSanPham {
    private int id;
    private String loai;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getLoai() {
        return loai;
    }

    public void setLoai(String loai) {
        this.loai = loai;
    }

    public LoaiSanPham(String loai) {
        this.loai = loai;
    }

    public LoaiSanPham() {
    }
}

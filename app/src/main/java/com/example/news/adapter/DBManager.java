package com.example.news.adapter;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;


import com.example.news.model.LoaiSanPham;
import com.example.news.model.SanPham;
import com.example.news.untils.Common;

import java.util.ArrayList;
import java.util.List;

public class DBManager extends SQLiteOpenHelper {

    private final Context context;

    private static String TABLE_NAME_SANPHAM = "sanpham";

    private static String ID = "id";
    private static String TENSANPHAM = "tensanpham";
    private static String HINHANH = "hinhanh";
    private static String LOAI = "loai";


    private static String TABLE_NAME_LOAI = "loai";
    private static String TENLOAI = "tenloai";


    public DBManager(Context context) {
        super(context, "banhang", null, 1);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sqlQuery = "CREATE TABLE " + TABLE_NAME_SANPHAM + " (" +
                ID + " integer primary key, " +
                TENSANPHAM + " TEXT, " +
                HINHANH + " BLOB, " +
                LOAI + " TEXT)";

        String sqlQueryLoai = "CREATE TABLE " + TABLE_NAME_LOAI + " (" +
                ID + " integer primary key, " +
                TENLOAI + " TEXT)";

        db.execSQL(sqlQuery);
        db.execSQL(sqlQueryLoai);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }

    public  void themLoaiSanPham(LoaiSanPham loaiSanPham){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(TENLOAI, loaiSanPham.getLoai());
        db.insert(TABLE_NAME_LOAI,null,values);
        db.close();
    }

    public  List<LoaiSanPham> getLoaiSanPham(){
        List<LoaiSanPham> listLoaiSP = new ArrayList<LoaiSanPham>();
        // Select All Query
        String selectQuery = "SELECT  *  FROM " + TABLE_NAME_LOAI;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                LoaiSanPham loaiSanPham = new LoaiSanPham();
                loaiSanPham.setId(cursor.getInt(0));
                loaiSanPham.setLoai(cursor.getString(1));
                listLoaiSP.add(loaiSanPham);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listLoaiSP;
    }



    public void insertSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HINHANH, Common.getBytes(sanPham.getHinhanh()));
        cv.put(TENSANPHAM, sanPham.getTensp());
        cv.put(LOAI, sanPham.getLoai());
        db.insert(TABLE_NAME_SANPHAM, null, cv);
        db.close();
    }

    public  ArrayList<SanPham> getSanPham(){
        ArrayList<SanPham> listSP = new ArrayList<SanPham>();
        // Select All Query
        String selectQuery = "SELECT  *  FROM " + TABLE_NAME_SANPHAM;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                SanPham sanPham = new SanPham();
                sanPham.setId(cursor.getInt(0));
                sanPham.setTensp(cursor.getString(1));
                sanPham.setHinhanh(Common.getPhoto(cursor.getBlob(2)));
                sanPham.setLoai(cursor.getString(3));
                listSP.add(sanPham);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return listSP;
    }


    public void updateSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(HINHANH, Common.getBytes(sanPham.getHinhanh()));
        cv.put(TENSANPHAM, sanPham.getTensp());
        cv.put(LOAI, sanPham.getLoai());
        db.update(TABLE_NAME_SANPHAM, cv,"id = "+sanPham.getId(), null);
        db.close();
    }

    public void xoaSanPham(SanPham sanPham) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME_SANPHAM, "id = "+sanPham.getId(), null);
        db.close();
    }

}
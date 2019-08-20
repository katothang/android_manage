package com.example.news;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.news.adapter.DBManager;
import com.example.news.model.LoaiSanPham;
import com.example.news.model.SanPham;
import com.example.news.untils.Common;

import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class SuaSanPhamActivity extends AppCompatActivity {
    DBManager dbManager;
    EditText editName;
    Spinner loai;
    Button btnThemSP;
    ImageView hinhanh;
    final int REQUEST_CODE_GALLERY = 999;
    Intent intent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sua_san_pham);
        editName = (EditText) findViewById(R.id.edtName);
        hinhanh = (ImageView) findViewById(R.id.imgHinhAnh);
        loai = (Spinner) findViewById(R.id.spLoai);
        intent = getIntent();

        editName.setText(intent.getStringExtra("ten"));
        hinhanh.setImageBitmap(Common.getPhoto(intent.getByteArrayExtra("hinhanh")));
        dbManager = new DBManager(getApplicationContext());
        List<String> list = new ArrayList<String>();

        for (LoaiSanPham loaiSanPham : dbManager.getLoaiSanPham()) {
            list.add(loaiSanPham.getLoai());
        }
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, list);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        loai.setAdapter(dataAdapter);
        btnThemSP = (Button) findViewById(R.id.btnThemSP);


        btnThemSP.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bitmap bm=((BitmapDrawable)hinhanh.getDrawable()).getBitmap();
                dbManager.updateSanPham(new SanPham(intent.getIntExtra("id",0), editName.getText().toString(), bm,loai.getSelectedItem().toString()));
                Toast.makeText(SuaSanPhamActivity.this, "Sửa thành công sản phẩm", Toast.LENGTH_SHORT).show();
            }
        });



        hinhanh.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ActivityCompat.requestPermissions(
                        SuaSanPhamActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        REQUEST_CODE_GALLERY
                );
            }
        });
    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == REQUEST_CODE_GALLERY){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, REQUEST_CODE_GALLERY);
            }
            else {
                Toast.makeText(getApplicationContext(), "Vui lòng cấp quyền", Toast.LENGTH_SHORT).show();
            }
            return;
        }

        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == REQUEST_CODE_GALLERY && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();

            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);

                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                hinhanh.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }
}

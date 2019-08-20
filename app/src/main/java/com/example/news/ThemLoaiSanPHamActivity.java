package com.example.news;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.example.news.adapter.DBManager;
import com.example.news.model.LoaiSanPham;

public class ThemLoaiSanPHamActivity extends AppCompatActivity {
    EditText edtTenLoai;
    Button btnThemLoai;
    DBManager dbManager;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_them_loai_san_pham);
        edtTenLoai = (EditText) findViewById(R.id.edtLoai);
        btnThemLoai = (Button) findViewById(R.id.btnThemLoai);

        btnThemLoai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(!edtTenLoai.getText().toString().equals("")){
                    dbManager = new DBManager(getApplicationContext());
                    dbManager.themLoaiSanPham(new LoaiSanPham(edtTenLoai.getText().toString()));
                    Toast.makeText(ThemLoaiSanPHamActivity.this, "Thêm Thành Công Loại Sản Phẩm "+ edtTenLoai.getText().toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }
}

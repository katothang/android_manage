package com.example.news;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.annotation.RequiresApi;
import android.support.design.widget.NavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.news.adapter.CustomAdapter;
import com.example.news.adapter.DBManager;
import com.example.news.model.SanPham;
import com.example.news.untils.Common;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    private ListView lvSanPham;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private DrawerLayout dl;
    private ActionBarDrawerToggle t;
    private NavigationView nv;
    DBManager dbManager;
    ArrayList<SanPham> arrSanPham;
    CustomAdapter customAdaper;
    int dem = 0;
    private static final int REQUEST_CODE_QR_SCAN = 101;
    private final String LOGTAG = "QRCScanner-MainActivity";

    @RequiresApi(api = Build.VERSION_CODES.M)
    @SuppressLint("WrongViewCast")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lvSanPham = (ListView) findViewById(R.id.lvsanpham);

        dbManager = new DBManager(getApplicationContext());
        arrSanPham = dbManager.getSanPham();


        customAdaper = new CustomAdapter(this, R.layout.row_listview_monhoc, arrSanPham);
        lvSanPham.setAdapter(customAdaper);

        // xóa sản phẩm
        lvSanPham.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, int i, long l) {
                dbManager.xoaSanPham(arrSanPham.get(dem));
                arrSanPham.remove(dem);
                arrSanPham = dbManager.getSanPham();
                customAdaper = new CustomAdapter(getApplicationContext(), R.layout.row_listview_monhoc, arrSanPham);
                lvSanPham.setAdapter(customAdaper);
                customAdaper.notifyDataSetChanged();
                Toast.makeText(MainActivity.this, "Xóa Thành Công.!", Toast.LENGTH_SHORT).show();
                return true;
            }
        });

        lvSanPham.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Intent intent = new Intent(getApplicationContext(), SuaSanPhamActivity.class);
                intent.putExtra("id", arrSanPham.get(i).getId());
                intent.putExtra("ten", arrSanPham.get(i).getTensp());
                intent.putExtra("hinhanh", Common.getBytes(arrSanPham.get(i).getHinhanh()));
                startActivity(intent);
            }
        });
        //set navication bar
        dl = (DrawerLayout) findViewById(R.id.activity_main);
        t = new ActionBarDrawerToggle(this, dl, R.string.Open, R.string.Close);

        dl.addDrawerListener(t);
        t.syncState();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        nv = (NavigationView) findViewById(R.id.nv);
        nv.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();
                switch (id) {
                    case R.id.themsanpham:
                        Intent intent = new Intent(getApplicationContext(), ThemSanPhamActivity.class);
                        startActivity(intent);
                      
                        break;
                    case R.id.themloai:
                        Intent intentSetting = new Intent(MainActivity.this,ThemLoaiSanPHamActivity.class);
                        startActivity(intentSetting);

                        break;
                    case R.id.lammoi:
                        arrSanPham = dbManager.getSanPham();
                        customAdaper = new CustomAdapter(getApplicationContext(), R.layout.row_listview_monhoc, arrSanPham);
                        lvSanPham.setAdapter(customAdaper);
                        customAdaper.notifyDataSetChanged();
                        break;
                    default:
                        break;
                }
                return true;
            }
        });


    }


    @Override
   public boolean onOptionsItemSelected(MenuItem item) {

        if (t.onOptionsItemSelected(item))
            return true;

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        arrSanPham = dbManager.getSanPham();
        customAdaper.notifyDataSetChanged();
        super.onBackPressed();
    }

    @Override
    protected void onRestart() {
        arrSanPham = dbManager.getSanPham();
        customAdaper.notifyDataSetChanged();
        super.onRestart();
    }

    @Override
    protected void onResume() {
        arrSanPham = dbManager.getSanPham();
        customAdaper.notifyDataSetChanged();
        super.onResume();
    }
}

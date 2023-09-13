package com.manager.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.manager.appbanhang.R;
import com.manager.appbanhang.Utils.Utils;
import com.manager.appbanhang.model.GioHang;
import com.manager.appbanhang.model.SanPhamMoi;
import com.nex3z.notificationbadge.NotificationBadge;

public class ChitietActivity extends AppCompatActivity {
    TextView txtTenSPCT,txtGiaCT,txtMotaCT;
    Spinner spnSoLuong;
    ImageView imgCT;
    androidx.appcompat.widget.Toolbar toolbar;
    Button btnThem;
    SanPhamMoi sanPhamMoi;
    NotificationBadge badge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chitiet);
        initView();
        getactionbar();
        initData();
        initControl();
    }

    private void initControl() {
        btnThem.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                themGioHang();
            }
        });
    }

    private void themGioHang() {
        if(Utils.gioHangList.size() > 0 ){
            boolean flag = false;
            int soL = Integer.parseInt(spnSoLuong.getSelectedItem().toString());
            for(int i = 0 ; i < Utils.gioHangList.size() ;i++){
                if(Utils.gioHangList.get(i).getIdsp()  == sanPhamMoi.getId()){
                    Utils.gioHangList.get(i).setSoluong(soL + Utils.gioHangList.get(i).getSoluong());
                    long gia = Long.parseLong(sanPhamMoi.getGiasp())*Utils.gioHangList.get(i).getSoluong();
//                    Utils.gioHangList.get(i).setGiasp(gia);
                    flag = true;
                }
            }
            if(flag == false){
                long gia = Long.parseLong(sanPhamMoi.getGiasp());
                GioHang gioHang = new GioHang();
                gioHang.setGiasp(gia);
                gioHang.setSoluong(soL);
                gioHang.setIdsp(sanPhamMoi.getId());
                gioHang.setTensp(sanPhamMoi.getTensp());
                gioHang.setHinhanh(sanPhamMoi.getHinhanh());
                Utils.gioHangList.add(gioHang);
            }
        }
        else {
            int soL = Integer.parseInt(spnSoLuong.getSelectedItem().toString());
            long gia = Long.parseLong(sanPhamMoi.getGiasp());
            GioHang gioHang = new GioHang();
            gioHang.setGiasp(gia);
            gioHang.setSoluong(soL);
            gioHang.setIdsp(sanPhamMoi.getId());
            gioHang.setTensp(sanPhamMoi.getTensp());
            gioHang.setHinhanh(sanPhamMoi.getHinhanh());
            Utils.gioHangList.add(gioHang);
        }
        int totalItem = 0;
        for(int i = 0 ; i< Utils.gioHangList.size();i++){
            totalItem = totalItem + Utils.gioHangList.get(i).getSoluong();
        }
        badge.setText(String.valueOf(totalItem));

    }

    private void initData() {
        sanPhamMoi = (SanPhamMoi) getIntent().getSerializableExtra("chitiet");
        txtTenSPCT.setText(sanPhamMoi.getTensp());
        txtMotaCT.setText(sanPhamMoi.getMota());
        txtGiaCT.setText( "Giá: " + sanPhamMoi.getGiasp()+" Đ");
        Glide.with(getApplicationContext()).load(sanPhamMoi.getHinhanh()).into(imgCT);
        Integer[] so = new Integer[]{1,2,3,4,5,6,7,8,9,10};
        ArrayAdapter<Integer> adapterSpn = new ArrayAdapter<>(this, androidx.appcompat.R.layout.support_simple_spinner_dropdown_item,so);
        spnSoLuong.setAdapter(adapterSpn);

    }

    private void initView() {
        txtGiaCT = findViewById(R.id.txtGiaCT);
        txtMotaCT = findViewById(R.id.txtMotaCT);
        txtTenSPCT = findViewById(R.id.txtTenSPCT);
        spnSoLuong = findViewById(R.id.spnSoLuong);
        imgCT = findViewById(R.id.imgChitiet);
        btnThem = findViewById(R.id.btnThemGioHang);
        toolbar = findViewById(R.id.toolbarCT);
        badge = findViewById(R.id.menu_sl);
        FrameLayout frameLayout = findViewById(R.id.frameGioHang);
        frameLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intentGH = new Intent(getApplicationContext(),GioHangActivity.class);
                startActivity(intentGH);
            }
        });
        if(Utils.gioHangList != null){
            int totalItem = 0;
            for(int i = 0 ; i< Utils.gioHangList.size();i++){
                totalItem = totalItem + Utils.gioHangList.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
    private void getactionbar() {
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(Utils.gioHangList != null){
            int totalItem = 0;
            for(int i = 0 ; i< Utils.gioHangList.size();i++){
                totalItem = totalItem + Utils.gioHangList.get(i).getSoluong();
            }
            badge.setText(String.valueOf(totalItem));
        }
    }
}
package com.manager.appbanhang.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.manager.appbanhang.R;
import com.manager.appbanhang.Utils.Utils;
import com.manager.appbanhang.retrofit.ApiBanHang;
import com.manager.appbanhang.retrofit.RetrofitClient;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.text.DecimalFormat;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class ThanhToanActivity extends AppCompatActivity {
    Toolbar toolbar_TT;
    TextView txtTongtien_TT,txtSoDT_TT,txtEmail_TT;
    EditText edtDiachi_TT;
    AppCompatButton btnDathang;
    ApiBanHang apiBanHang;
    CompositeDisposable compositeDisposable = new CompositeDisposable();
    long tongTien ;
    int totalItem;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_thanh_toan);

        initview();
        countItem();
        initControl();
    }

    private void countItem() {
         totalItem = 0;
        for(int i = 0; i< Utils.muaHangList.size(); i++){
            totalItem = totalItem + Utils.muaHangList.get(i).getSoluong();
        }
    }

    private void initControl() {
        setSupportActionBar(toolbar_TT);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_TT.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        DecimalFormat decimalFormat = new DecimalFormat("###,###,###");
        tongTien = getIntent().getLongExtra("tongtien",0);
        txtTongtien_TT.setText(decimalFormat.format(tongTien));
        txtEmail_TT.setText(Utils.user.getEmail());
        txtSoDT_TT.setText(Utils.user.getMobile());

        btnDathang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String strDiachi = edtDiachi_TT.getText().toString().trim();
                if(TextUtils.isEmpty(strDiachi)){
                    Toast.makeText(ThanhToanActivity.this, "Bạn chưa nhập địa chỉ giao hàng", Toast.LENGTH_SHORT).show();
                }
                else {
                    // post data
                    String email = Utils.user.getEmail();
                    String sdt = Utils.user.getMobile();
                    int iduser = Utils.user.getId();
                    Gson gson = new GsonBuilder()
                            .setLenient()
                            .create();

                    Log.d("test",new Gson().toJson(Utils.muaHangList));
                    compositeDisposable.add(apiBanHang.creatOder(email,sdt,String.valueOf(tongTien),iduser,strDiachi,totalItem,gson.toJson(Utils.muaHangList))
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe(
                                    userModel -> {
                                        if(userModel.isSuccess()){
                                            Toast.makeText(ThanhToanActivity.this, "Thành công", Toast.LENGTH_SHORT).show();
                                            Utils.muaHangList.clear();
                                            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
                                            startActivity(intent);
                                            finish();
                                        }
                                        else {
                                            Toast.makeText(ThanhToanActivity.this, userModel.getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    },
                                    throwable -> {
                                        Toast.makeText(ThanhToanActivity.this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                                    }
                            ));
                }
            }
        });
    }

    private void initview() {
        txtEmail_TT = findViewById(R.id.txtEmail_TT);
        txtSoDT_TT = findViewById(R.id.txtSoDT_TT);
        txtTongtien_TT = findViewById(R.id.txtTongtien_TT);
        edtDiachi_TT = findViewById(R.id.edtDiachi_TT);
        btnDathang = findViewById(R.id.btnDatHang);
        toolbar_TT = findViewById(R.id.toolbarTT);
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        compositeDisposable.clear();
    }
}
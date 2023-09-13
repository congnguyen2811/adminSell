package com.manager.appbanhang.activity;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.github.dhaval2404.imagepicker.ImagePicker;
import com.manager.appbanhang.R;
import com.manager.appbanhang.Utils.Utils;
import com.manager.appbanhang.adapter.DienThoaiAdapter;
import com.manager.appbanhang.databinding.ActivityThemSpactivityBinding;
import com.manager.appbanhang.model.MessageModel;
import com.manager.appbanhang.model.SanPhamMoi;
import com.manager.appbanhang.retrofit.ApiBanHang;
import com.manager.appbanhang.retrofit.RetrofitClient;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.disposables.CompositeDisposable;
import io.reactivex.rxjava3.schedulers.Schedulers;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ThemSPActivity extends AppCompatActivity {
    Spinner spnLoai_themsp;
    int loai = 0;
    ActivityThemSpactivityBinding binding;
    EditText edtTensp, edtGia, edtHinhanh, edtMota;
    Button btnThemsp;
    ApiBanHang apiBanHang;
    SanPhamMoi sanPhamSua;
    String mediaPath;
    boolean flag = false;
    Toolbar toolbar_themSP;

    CompositeDisposable compositeDisposable = new CompositeDisposable();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityThemSpactivityBinding.inflate(getLayoutInflater());
        apiBanHang = RetrofitClient.getInstance(Utils.BASE_URL).create(ApiBanHang.class);
        setContentView(binding.getRoot());


        initview();
        initdata();
        getactionbar();
        Intent intent = getIntent();
        sanPhamSua = (SanPhamMoi) intent.getSerializableExtra("sua");
        if (sanPhamSua == null) {
            flag = false;
        } else {
            flag = true;
            binding.btnThemThemsp.setText("Sửa sản phẩm");
            // show data
            binding.edtTenspThemsp.setText(sanPhamSua.getTensp());
            binding.edtGiaThemsp.setText(sanPhamSua.getGiasp()+"");
            binding.edtMotaThemsp.setText(sanPhamSua.getMota());
            binding.edtHinhanhThemsp.setText(sanPhamSua.getHinhanh());
            binding.spnLoaispThem.setSelection(sanPhamSua.getLoai());
            binding.edtsoluongThemsp.setText(sanPhamSua.getSoluongtonkho()+"");
            binding.edtlinkThemsp.setText(sanPhamSua.getLinkvideo());
        }
    }

    private void getactionbar() {
        setSupportActionBar(toolbar_themSP);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar_themSP.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initdata() {
        List<String> list = new ArrayList<>();
        list.add("Vui lòng chọn loại sản phẩm");
        list.add(("Loại 1"));
        list.add(("Loại 2"));
        ArrayAdapter arrayAdapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_dropdown_item, list);
        spnLoai_themsp.setAdapter(arrayAdapter);
        spnLoai_themsp.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                loai = position;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        binding.btnThemThemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (flag == false) {
                    themSP();
                } else {
                    suaSp();
                }

            }
        });
        binding.imgHinhanhThemsp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImagePicker.with(ThemSPActivity.this)
                        .crop()                    //Crop image(Optional), Check Customization for more option
                        .compress(1024)            //Final image size will be less than 1 MB(Optional)
                        .maxResultSize(1080, 1080)    //Final image resolution will be less than 1080 x 1080(Optional)
                        .start();
            }
        });
    }

    private void suaSp() {
        String strTen = binding.edtTenspThemsp.getText().toString().trim();
        String strGia = binding.edtGiaThemsp.getText().toString().trim();
        String strHinhanh = binding.edtHinhanhThemsp.getText().toString().trim();
        String strMota = binding.edtMotaThemsp.getText().toString().trim();
        String strSoluong = binding.edtsoluongThemsp.getText().toString().trim();
        String strlinkvideo = binding.edtlinkThemsp.getText().toString().trim();
        if (TextUtils.isEmpty(strTen) || TextUtils.isEmpty(strGia) || TextUtils.isEmpty(strHinhanh) || TextUtils.isEmpty(strMota) ||TextUtils.isEmpty(strSoluong) || loai == 0) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiBanHang.updateSP(strTen, strGia, strHinhanh, strMota,loai,sanPhamSua.getId(),Integer.parseInt(strSoluong),strlinkvideo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()) {
                                    Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), QuanLyActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mediaPath = data.getDataString();
        uploadMultipleFiles();
        Log.d("log", "onActivityResult" + mediaPath);
    }

    private void themSP() {
        String strTen = binding.edtTenspThemsp.getText().toString().trim();
        String strGia = binding.edtGiaThemsp.getText().toString().trim();
        String strHinhanh = binding.edtHinhanhThemsp.getText().toString().trim();
        String strMota = binding.edtMotaThemsp.getText().toString().trim();
        String strSoluong = binding.edtsoluongThemsp.getText().toString().trim();
        String strlinkvideo = binding.edtlinkThemsp.getText().toString().trim();
        if (TextUtils.isEmpty(strTen) || TextUtils.isEmpty(strGia) || TextUtils.isEmpty(strHinhanh) || TextUtils.isEmpty(strMota) || TextUtils.isEmpty(strSoluong) || loai == 0) {
            Toast.makeText(this, "Vui lòng nhập đủ thông tin", Toast.LENGTH_SHORT).show();
        } else {
            compositeDisposable.add(apiBanHang.insertSP(strTen, strGia, strHinhanh, strMota, loai,Integer.parseInt(strSoluong),strlinkvideo)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(
                            messageModel -> {
                                if (messageModel.isSuccess()) {
                                    Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), QuanLyActivity.class);
                                    startActivity(intent);
                                    finish();
                                } else {
                                    Toast.makeText(this, messageModel.getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            },
                            throwable -> {
                                Toast.makeText(this, throwable.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                    ));
        }
    }

    private String getPath(Uri uri) {
        String result;
        Cursor cursor = getContentResolver().query(uri, null, null, null, null);
        if (cursor == null) {
            result = uri.getPath();
        } else {
            cursor.moveToFirst();
            int index = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            result = cursor.getString(index);
            cursor.close();
        }
        return result;
    }

    private void uploadMultipleFiles() {

        Uri uri = Uri.parse(mediaPath);
        File file = new File(getPath(uri));
        // Parsing any Media type file
        RequestBody requestBody1 = RequestBody.create(MediaType.parse("*/*"), file);
        MultipartBody.Part fileToUpload1 = MultipartBody.Part.createFormData("file", file.getName(), requestBody1);
        Call<MessageModel> call = apiBanHang.uploadFile(fileToUpload1);
        call.enqueue(new Callback<MessageModel>() {
            @Override
            public void onResponse(Call<MessageModel> call, Response<MessageModel> response) {
                MessageModel serverResponse = response.body();
                if (serverResponse != null) {
                    if (serverResponse.isSuccess()) {
                        binding.edtHinhanhThemsp.setText(serverResponse.getName());
                    } else {
                        Toast.makeText(getApplicationContext(), serverResponse.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                } else {

                    Log.d("Response", serverResponse.toString());
                }
            }

            @Override
            public void onFailure(Call<MessageModel> call, Throwable t) {
                Log.d("log", t.getMessage());
            }
        });
    }

    private void initview() {
        spnLoai_themsp = findViewById(R.id.spnLoaisp_Them);
        toolbar_themSP = findViewById(R.id.toolbar_ThemSP);

    }

    @Override
    protected void onDestroy() {
        compositeDisposable.clear();
        super.onDestroy();
    }
}
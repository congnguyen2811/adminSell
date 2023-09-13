package com.manager.appbanhang.retrofit;

import com.manager.appbanhang.model.DonHangModel;
import com.manager.appbanhang.model.LoaiSPModel;
import com.manager.appbanhang.model.MessageModel;
import com.manager.appbanhang.model.SanPhamMoiModel;
import com.manager.appbanhang.model.ThongKeModel;
import com.manager.appbanhang.model.UserModel;

import io.reactivex.rxjava3.core.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface ApiBanHang {
    @GET("getloaiSP.php")
    Observable<LoaiSPModel> getLoaiSP();
    @GET("getspmoi.php")
    Observable<SanPhamMoiModel> getSPMoi();

    @POST("chitiet.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> getDienThoai(
            @Field("page") int page,
            @Field("loai") int loai
    );

    @POST("dangki.php")
    @FormUrlEncoded
    Observable<UserModel> dangKi(
            @Field("email") String email,
            @Field("pass") String pass,
            @Field("username") String username,
            @Field("mobile") String mobile,
            @Field("uid") String uid
    );

    @POST("dangnhap.php")
    @FormUrlEncoded
    Observable<UserModel> dangNhap(
            @Field("email") String email,
            @Field("pass") String pass
    );

    @POST("reset.php")
    @FormUrlEncoded
    Observable<UserModel> resetPass(
            @Field("email") String email
    );

    @POST("donhang.php")
    @FormUrlEncoded
    Observable<UserModel> creatOder(
            @Field("email") String email,
            @Field("sdt") String sdt,
            @Field("tongtien") String tongtien,
            @Field("iduser") int iduser,
            @Field("diachi") String diachi,
            @Field("soluong") int soluong,
            @Field("chitiet") String chitiet
    );

    @POST("xemdonhang.php")
    @FormUrlEncoded
    Observable<DonHangModel> xemDonHang(
            @Field("iduser") int id
    );

    @POST("timkiem.php")
    @FormUrlEncoded
    Observable<SanPhamMoiModel> searchProducts(
            @Field("search") String search
    );

    @POST("xoa.php")
    @FormUrlEncoded
    Observable<MessageModel> xoaSp(
            @Field("id") int id
    );
    @POST("insertsp.php")
    @FormUrlEncoded
    Observable<MessageModel> insertSP(
            @Field("tensp") String tensp,
            @Field("gia") String gia,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai,
            @Field("slsp") int soluong,
            @Field("linkvideo") String linkvideo
    );
    @POST("updatesp.php")
    @FormUrlEncoded
    Observable<MessageModel> updateSP(
            @Field("tensp") String tensp,
            @Field("gia") String gia,
            @Field("hinhanh") String hinhanh,
            @Field("mota") String mota,
            @Field("loai") int loai,
            @Field("id") int id,
            @Field("soluongtonkho") int soluong,
            @Field("linkvideo") String linkvideo

    );
    @POST("gettoken.php")
    @FormUrlEncoded
    Observable<UserModel> getToken(
            @Field("status") int status,
            @Field("iduser") int iduser
    );
    @POST("updatetoken.php")
    @FormUrlEncoded
    Observable<MessageModel> updateToken(
            @Field("id") int id,
            @Field("token") String token
    );

    @Multipart
    @POST("upload.php")
    Call<MessageModel> uploadFile(@Part MultipartBody.Part file);

    @POST("updatedonhang.php")
    @FormUrlEncoded
    Observable<MessageModel> updatetrangthaiDH(
            @Field("id") int id,
            @Field("trangthai") int trangthai
    );
    @GET("thongke.php")
    Observable<ThongKeModel> thongkeSP();

    @GET("thongkethang.php")
    Observable<ThongKeModel> thongkedoanhthuThang();

}

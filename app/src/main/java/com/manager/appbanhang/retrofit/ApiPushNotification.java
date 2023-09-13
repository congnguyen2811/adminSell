package com.manager.appbanhang.retrofit;



import com.manager.appbanhang.model.NotiReponse;
import com.manager.appbanhang.model.NotiSendData;

import io.reactivex.rxjava3.core.Observable;
import retrofit2.http.Body;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface ApiPushNotification {
    @Headers(
            {
            "Content-Type: application/json",
            "Authorization: key=AAAAi2Gfkbw:APA91bFKp4FQpDUVDHXXe-3YRhUiAJqSpez6yljo3HMrpa0t_BMjfWq1F_1D2sNEbkwcwDNnJIwmjafeJwMT2_jMi5tR6XIoXQmmWlWywF1hNhr7V3ZagoE9GowAkU0aqi4564oDxoK8"
            }
    )
    @POST("fcm/send")
    Observable<NotiReponse> sendNotification(@Body NotiSendData data);
}

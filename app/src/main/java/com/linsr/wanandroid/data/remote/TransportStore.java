package com.linsr.wanandroid.data.remote;

import com.linsr.linlibrary.model.ResponsePojo;
import com.linsr.wanandroid.data.model.CommonVo;
import com.linsr.wanandroid.data.model.UserPojo;

import java.util.Map;

import io.reactivex.Observable;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Query;

/**
 * Description
 @author Linsr
 */

public interface TransportStore {

    String HOST = "http://114.67.139.83:8888/";


    @FormUrlEncoded
    @POST("user/register")
    Observable<ResponsePojo<UserPojo>> register(@Field("phone") String phone,
                                              @Field("password") String password,
                                              @Field("code") String code);

    @POST("user/login")
    Observable<ResponsePojo<UserPojo>> login(@Body RequestBody info);

    @GET("user/logout")
    Observable<ResponsePojo<Object>> logout(@Query("userCode") String userCode);

    @GET("user/checkcode")
    Observable<ResponsePojo> identifyingCode(@Query("phone") String phone);

    @POST("auth/driving")
    @Multipart
    Observable<ResponsePojo<CommonVo>> driving(@Part("dirName") String phone,
                                               @Part("userCode") String userCode,
                                               @Part MultipartBody.Part file);

    @POST("auth/vehicle")
    @Multipart
    Observable<ResponsePojo<CommonVo>> vehicle(@Part("dirName") String phone,
                                               @Part("userCode") String userCode,
                                               @Part MultipartBody.Part file);

    @POST("auth/idcard")
    @Multipart
    Observable<ResponsePojo<CommonVo>> idcard(@Part("dirName") String phone,
                                              @Part("userCode") String userCode,
                                              @Part MultipartBody.Part file);

    @POST("auth/safeform")
    @Multipart
    Observable<ResponsePojo> safeform(@Part("dirName") String phone,
                                      @Part("userCode") String userCode,
                                      @Part MultipartBody.Part file);

    @Headers({"Content-Type: application/json","Accept: application/json"})//需要添加头
    @POST("auth/vehicleinfo")
    Observable<ResponsePojo> vehicleinfo(@Body RequestBody body);

    @GET("user/info")
    Observable<ResponsePojo<UserPojo>> getUserInfo(@Query("userCode") String userCode);

}

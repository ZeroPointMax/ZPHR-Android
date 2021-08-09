package de.zeropointmax.zphr;

import retrofit2.Call;
import retrofit2.http.*;

public interface ApiService {
    @GET("volDigital")
    Call<Integer> getVolumeDigital();

    @POST("volDigital")
    @FormUrlEncoded
    Call<Integer> setVolumeDigital(@Field("vol") Integer volDigital);

    @GET("volHeadphone")
    Call<Integer> getVolumeHeadphone();

    @POST("volHeadphone")
    @FormUrlEncoded
    Call<Integer> setVolumeHeadphone(@Field("vol") Integer volHeadphone);

    @GET("mute")
    Call<Short> getMute();

    @POST("mute")
    @FormUrlEncoded
    Call<Short> setMute(@Field("vol") Short mute);

    @GET("analogBooster1")
    Call<Short> getAB1();

    @POST("analogBooster1")
    @FormUrlEncoded
    Call<Short> setAB1(@Field("vol") Short ab1);

    @GET("analogBooster2")
    Call<Short> getAB2();

    @POST("analogBooster2")
    @FormUrlEncoded
    Call<Short> setAB2(@Field("vol") Short ab2);
}

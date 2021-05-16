package de.zeropointmax.zphr;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiService {
    @GET("volDigital")
    Call<Integer> getVolumeDigital();

    @POST("volDigital")
    Call<Integer> setVolumeDigital(@Body Integer volDigital);

    @GET("volHeadphone")
    Call<Integer> getVolumeHeadphone();

    @POST("volHeadphone")
    Call<Integer> setVolumeHeadphone(@Body Integer volHeadphone);

    @GET("mute")
    Call<Short> getMute();

    @POST("mute")
    Call<Short> setMute(@Body Short mute);

    @GET("analogBooster1")
    Call<Short> getAB1();

    @POST("analogBooster1")
    Call<Short> setAB1();

    @GET("analogBooster2")
    Call<Short> getAB2();

    @POST("analogBooster2")
    Call<Short> setAB2();
}

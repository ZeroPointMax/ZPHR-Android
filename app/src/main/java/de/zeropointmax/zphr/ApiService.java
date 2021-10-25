package de.zeropointmax.zphr;

import retrofit2.Call;
import retrofit2.http.*;

/**
 * Retrofit2-Interface to declare (not define) API Endpoint Logic.
 * Long-Term-TODO: split this interface into children for different DACs
 */
public interface ApiService {
    /**
     * /volDigital Endpoint Getter.
     * @return Input Volume, a number between 0 and 100
     */
    @GET("volDigital")
    Call<Integer> getVolumeDigital();

    /**
     * /volDigital Endpoint Setter.
     * @param volDigital the desired input volume, a number between 0 and 100.
     * @return the volume after the Setter has run, a number between 0 and 100.
     */
    @POST("volDigital")
    @FormUrlEncoded
    Call<Integer> setVolumeDigital(@Field("vol") Integer volDigital);

    /**
     * /volHdph Endpoint Getter.
     * @return the volume of the headphones output. A number between 0 and 100.
     */
    @GET("volHeadphone")
    Call<Integer> getVolumeHeadphone();

    /**
     * /volHdph Endpoint Setter.
     * @param volHeadphone the desired headphones volume, a number between 0 and 100.
     * @return the volume of the headphones output after the Setter has run. a number between 0 and 100
     */
    @POST("volHeadphone")
    @FormUrlEncoded
    Call<Integer> setVolumeHeadphone(@Field("vol") Integer volHeadphone);

    /**
     * /mute Endpoint Getter.
     * @return the mute status, either 0 or 1
     */
    @GET("mute")
    Call<Short> getMute();

    /**
     * /mute Endpoint Setter.
     * @param mute the desired mute status, either 0 or 1 (Short)
     * @return the mute status after the Setter has run, either 0 or 1
     */
    @POST("mute")
    @FormUrlEncoded
    Call<Short> setMute(@Field("mute") Short mute);

    /**
     * /ab1 Endpoint Getter.
     * There are two Boosters on the HifiBerry. They make the output a little louder but other than that,
     * the function of these is currently unknown.
     * This function interacts with AB1
     * @return the status of Analogue Booster 1, either 0 or 1
     */
    @GET("analogBooster1")
    Call<Short> getAB1();

    /**
     * /ab1 Endpoint Setter.
     * There are two Boosters on the HifiBerry. They make the output a little louder but other than that,
     * the function of these is currently unknown.
     * This function interacts with AB1
     * @param ab1 the desired state of Analogue Booster 1
     * @return the status of Analogue Booster 1 after the Setter has run, either 0 or 1
     */
    @POST("analogBooster1")
    @FormUrlEncoded
    Call<Short> setAB1(@Field("vol") Short ab1);

    /**
     * /ab2 Endpoint Getter.
     * There are two Boosters on the HifiBerry. They make the output a little louder but other than that,
     * the function of these is currently unknown.
     * This function interacts with AB2
     * @return the status of Analogue Booster 2, either 0 or 1
     */
    @GET("analogBooster2")
    Call<Short> getAB2();

    /**
     * /ab2 Endpoint Setter.
     * There are two Boosters on the HifiBerry. They make the output a little louder but other than that,
     * the function of these is currently unknown.
     * This function interacts with AB2
     * @param ab2 the desired state of Analogue Booster 2
     * @return the status of Analogue Booster 2 after the Setter has run, either 0 or 1
     */
    @POST("analogBooster2")
    @FormUrlEncoded
    Call<Short> setAB2(@Field("vol") Short ab2);

    /**
     * API Endpoint to reboot the host
     * @return the exit code of the shutdown command
     */
    @GET("reboot")
    Call<Integer> reboot();

    /**
     * API endpoint to shut down the host
     * @return the exit code of the shutdown command
     */
    @GET("shutdown")
    Call<Integer> shutdown();
}

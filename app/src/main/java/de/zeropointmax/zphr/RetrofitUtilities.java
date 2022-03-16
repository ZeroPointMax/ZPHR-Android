package de.zeropointmax.zphr;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * holds logic and members needed for using Retrofit2
 */
public class RetrofitUtilities {

    /**
     * initializes Retrofit2 and create instance of ApiService, implemented by Retrofit2
     * @param baseUrl the URL of the backend to use
     * @return initialized instance of ApiService
     * @throws IllegalArgumentException thrown when URL is malformed. For example, "http://" is missing.
     */
    public static ApiService initializeRetrofit(String baseUrl) throws IllegalArgumentException {
        return new Retrofit.Builder()
                .baseUrl(baseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(ApiService.class);
    }
}

package com.scoutzknifez.tictactoe.utility;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Utils {
    public static <T> T test(Class<T> clazz) {
        OkHttpClient client = new OkHttpClient.Builder().build();
        return new Retrofit
                .Builder()
                .baseUrl("http://10.0.2.2:8080")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build()
                .create(clazz);
    }
}

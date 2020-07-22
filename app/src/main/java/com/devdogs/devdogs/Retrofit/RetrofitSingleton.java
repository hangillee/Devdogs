package com.devdogs.devdogs.Retrofit;

import android.content.Context;

import com.devdogs.devdogs.Cookie.AddCookies;
import com.devdogs.devdogs.Cookie.StoreCookies;
import com.devdogs.devdogs.DATA;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitSingleton {
    private static RetrofitSingleton instance = null;
    private OkHttpClient client;
    public Retrofit retrofit;
    private Gson gson;

    private RetrofitSingleton(){}

    public static void init(Context context){
        instance.client = new OkHttpClient.Builder()
                .addInterceptor(new AddCookies(context))
                .addInterceptor(new StoreCookies(context))
                .build();
        instance.retrofit = new Retrofit.Builder()
                .baseUrl("http://" + DATA.getURL() + "/")
                .client(instance.client)
                .addConverterFactory(GsonConverterFactory.create(instance.gson))
                .build();
    }
    public static RetrofitSingleton getInstance() {
        if (instance == null) {
            instance = new RetrofitSingleton();
            instance.gson = new GsonBuilder().setLenient().create();
        }
        return instance;
    }

    public static RetrofitSingleton getInstance(Context context) {
        if (instance == null) {
            instance = new RetrofitSingleton();
            instance.gson = new GsonBuilder().setLenient().create();
            init(context);
        }
        return instance;
    }
}

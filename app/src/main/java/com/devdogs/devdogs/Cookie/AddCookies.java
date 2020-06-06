package com.devdogs.devdogs.Cookie;

import android.content.Context;
import android.preference.PreferenceManager;

import java.io.IOException;
import java.util.HashSet;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookies implements Interceptor {
    public static final String PREF_COOKIES = "PREF_COOKIES";
    private Context context;

    public AddCookies(Context context) {
        this.context = context;
    }

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Request.Builder builder = chain.request().newBuilder();

        HashSet<String> prefrences = (HashSet<String>) PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext()).getStringSet(PREF_COOKIES, new HashSet<String>());

        for (String cookie : prefrences) {
            builder.addHeader("Cookie", cookie);
        }
        return chain.proceed(builder.build());
    }
}

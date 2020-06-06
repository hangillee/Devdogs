package com.devdogs.devdogs.retroit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface LoginService {
    @POST("users/login")
    @FormUrlEncoded
    Call<Void> login(@Field("id") String id, @Field("pw") String pw);
}

package com.devdogs.devdogs.retroit;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;

public interface SubmitService {
    @POST("submit")
    @FormUrlEncoded
    Call<Void> submit(@Field("title") String title, @Field("content") String content);
}

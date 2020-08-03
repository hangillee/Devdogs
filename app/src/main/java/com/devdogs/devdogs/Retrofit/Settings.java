package com.devdogs.devdogs.Retrofit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Settings {
    @SerializedName("isAuthorized")
    @Expose
    private boolean isAutorized = false;

    public boolean isAuthorized() {
        return isAutorized;
    }
}

package com.devdogs.devdogs.retroit;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Submit {
    @SerializedName("title")
    @Expose
    private String title;

    @SerializedName("content")
    @Expose
    private String content;
}

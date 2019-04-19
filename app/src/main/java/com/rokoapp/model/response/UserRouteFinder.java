package com.rokoapp.model.response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class UserRouteFinder {
    @SerializedName("status_code")
    private String status_code;
    @SerializedName("data")
    private List<RouteFinderModel> data;

    public String getStatus_code() { return status_code; }

    public List<RouteFinderModel> getData() { return data; }
}

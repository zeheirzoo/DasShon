package com.example.project.controllers;


import org.json.JSONObject;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface RetrofitRequest {

    @Multipart
    @POST("YOUR_URL/")
    Call<ResponseBody> uploadImages(@Header ("Accept") String type,@Header("Authorization")String token,@Body JSONObject object, @Part List<MultipartBody.Part> images);
}

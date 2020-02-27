package com.example.expandablelayout.NetworkRequest;

import com.example.expandablelayout.GetCategories;

import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface ApiInterface {

    @GET("/get_categories")
    Call<GetCategories> getCategories();
}

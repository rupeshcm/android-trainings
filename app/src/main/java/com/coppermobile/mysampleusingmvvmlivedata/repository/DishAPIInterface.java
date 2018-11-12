package com.coppermobile.mysampleusingmvvmlivedata.repository;

import com.coppermobile.mysampleusingmvvmlivedata.data.source.remote.DishResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface DishAPIInterface {

    @GET("json/menu.json")
    Call<List<DishResponse>> getDishes();
}
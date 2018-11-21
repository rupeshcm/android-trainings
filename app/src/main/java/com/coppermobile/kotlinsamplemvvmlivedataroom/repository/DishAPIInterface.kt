package com.coppermobile.kotlinsamplemvvmlivedataroom.repository

import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.remote.DishResponse
import retrofit2.Call
import retrofit2.http.GET

interface DishAPIInterface {

    @GET("json/menu.json")
    fun getDishes(): Call<List<DishResponse>>?
}
package com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.remote

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.DataSource
import com.coppermobile.kotlinsamplemvvmlivedataroom.repository.DishAPIInterface
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class RemoteDishDataSource private constructor() : DataSource<Dish> {

    private val url = "https://api.androidhive.info/"

    private object Holder {
        val INSTANCE = RemoteDishDataSource()
    }

    companion object {
        val instance: RemoteDishDataSource by lazy { Holder.INSTANCE }
    }

    override fun insertData(data: Dish) {

    }

    override fun insertData(data: List<Dish>) {

    }

    override fun deleteData(data: Dish) {

    }

    override fun getComments(id: Int): LiveData<List<Comments>>? {
        return null
    }

    override fun getAllDishes(): LiveData<List<Dish>> {

        val gson: Gson = GsonBuilder().setLenient().create()
        val retrofit: Retrofit = Retrofit.Builder().baseUrl(url).addConverterFactory(GsonConverterFactory.create(gson)).build()
        val dishAPIInterface: DishAPIInterface = retrofit.create(DishAPIInterface::class.java)

        val mutableLiveData = MutableLiveData<List<Dish>>()
        dishAPIInterface.getDishes()?.enqueue(object : Callback<List<DishResponse>> {
            override fun onResponse(call: Call<List<DishResponse>>, response: Response<List<DishResponse>>) {
                val dishResponse = response.body()
                if (dishResponse != null) {
                    val dishList = ArrayList<Dish>()
                    dishResponse.indices.forEach { i ->
                        val dish = Dish()
                        dish.id = dishResponse[i].id!!
                        dish.dishName = dishResponse[i].name
                        dish.dishDescription = dishResponse[i].description
                        dish.dishImage = dishResponse[i].thumbnail
                        dish.dishPrice = dishResponse[i].price!!
                        dishList.add(dish)
                    }
                    mutableLiveData.value = dishList
                }
            }

            override fun onFailure(call: Call<List<DishResponse>>, t: Throwable) {
                mutableLiveData.value = null
            }
        })
        return mutableLiveData
    }
}
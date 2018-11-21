package com.coppermobile.kotlinsamplemvvmlivedataroom.data.source

import android.arch.lifecycle.LiveData
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish

interface DataSource<T> {

    fun insertData(data: T)

    fun insertData(data: List<T>)

    fun deleteData(data: T)

    fun getComments(id: Int): LiveData<List<Comments>>?

    fun getAllDishes(): LiveData<List<Dish>>
}
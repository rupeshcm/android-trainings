package com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish

@Dao
interface DishDao {

    @Query("select * from dish_table")
    fun getAllDishes(): LiveData<List<Dish>>

    @Insert
    fun insertDishes(dishList: List<Dish>)
}
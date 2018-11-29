package com.coppermobile.mysamplemvvmdatabindinglivedata.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;


import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;

import java.util.List;

@Dao
interface DishDAO {

    @Insert
    void insertDishes(List<Dish> dishList);

    @Query("select * from dish")
    LiveData<List<Dish>> getAllDishes();
}
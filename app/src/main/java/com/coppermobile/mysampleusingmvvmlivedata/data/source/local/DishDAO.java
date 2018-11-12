package com.coppermobile.mysampleusingmvvmlivedata.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;

import java.util.List;

@Dao
interface DishDAO {

    @Insert
    void insertDishes(List<Dish> dishList);

    @Query("select * from dish_table")
    LiveData<List<Dish>> getAllDishes();
}
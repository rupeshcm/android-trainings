package com.coppermobile.mysampleusingmvvmlivedata.data.source;

import android.arch.lifecycle.LiveData;

import com.coppermobile.mysampleusingmvvmlivedata.data.Comments;
import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;
import com.coppermobile.mysampleusingmvvmlivedata.data.source.remote.DishResponse;

import java.util.List;

public interface DataSource<T> {

    void insertData(T data);

    void insertData(List<T> data);

    void deleteData(T data);

    LiveData<List<Comments>> getComments(int id);

    LiveData<List<Dish>> getAllDishes();
}
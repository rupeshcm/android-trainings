package com.coppermobile.mysamplemvvmdatabindinglivedata.data.source;

import android.arch.lifecycle.LiveData;

import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Comments;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;

import java.util.List;

public interface DataSource<T> {

    void insertData(T data);

    void insertData(List<T> data);

    void deleteData(T data);

    LiveData<List<Comments>> getComments(int id);

    LiveData<List<Dish>> getAllDishes();
}
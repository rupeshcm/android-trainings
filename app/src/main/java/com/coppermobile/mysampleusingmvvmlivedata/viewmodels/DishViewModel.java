package com.coppermobile.mysampleusingmvvmlivedata.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Observer;
import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;

import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;
import com.coppermobile.mysampleusingmvvmlivedata.data.source.remote.DishResponse;
import com.coppermobile.mysampleusingmvvmlivedata.repository.DishRepository;

import java.util.List;

public class DishViewModel extends AndroidViewModel {

    private final DishRepository mTasksRepository;

    public DishViewModel(Application context, DishRepository repository) {
        super(context);
        mTasksRepository = repository;
    }

    public LiveData<List<Dish>> getAllDish() {
        return mTasksRepository.getAllDishes();
    }
}
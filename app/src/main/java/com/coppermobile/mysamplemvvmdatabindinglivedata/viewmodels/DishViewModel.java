package com.coppermobile.mysamplemvvmdatabindinglivedata.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;

import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;
import com.coppermobile.mysamplemvvmdatabindinglivedata.repository.DishRepository;

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
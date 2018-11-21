package com.coppermobile.kotlinsamplemvvmlivedataroom.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.repository.DishRepository

class DishViewModel(context: Application, private var mTasksRepository: DishRepository) : AndroidViewModel(context) {

    fun getAllDishes(): LiveData<List<Dish>>? {
        return mTasksRepository.getAllDishes()
    }
}
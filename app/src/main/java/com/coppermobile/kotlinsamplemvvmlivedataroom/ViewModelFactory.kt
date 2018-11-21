package com.coppermobile.kotlinsamplemvvmlivedataroom

import android.app.Application
import android.arch.lifecycle.ViewModel
import android.arch.lifecycle.ViewModelProvider
import com.coppermobile.kotlinsamplemvvmlivedataroom.utils.Injection
import com.coppermobile.kotlinsamplemvvmlivedataroom.viewmodels.CommentsViewModel
import com.coppermobile.kotlinsamplemvvmlivedataroom.viewmodels.DishViewModel

class ViewModelFactory private constructor(private val application: Application) : ViewModelProvider.NewInstanceFactory() {

    companion object {

        @Volatile
        private var INSTANCE: ViewModelFactory? = null

        fun getInstance(application: Application): ViewModelFactory {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = ViewModelFactory(application)
                }
            }
            return INSTANCE!!
        }
    }

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(DishViewModel::class.java)) {
            return DishViewModel(application, Injection.provideDishRepository(application)) as T
        } else if (modelClass.isAssignableFrom(CommentsViewModel::class.java)) {
            return CommentsViewModel(application, Injection.provideCommentsRepository(application)) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class: " + modelClass.name)
    }
}
package com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.local

import android.arch.lifecycle.LiveData
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.DataSource
import com.coppermobile.kotlinsamplemvvmlivedataroom.utils.AppExecutors

class LocalDishDataSource private constructor(private val appExecutors: AppExecutors, private val dishDao: DishDao) : DataSource<Dish> {

    companion object {

        @Volatile
        private var INSTANCE: LocalDishDataSource? = null

        fun getInstance(appExecutors: AppExecutors, dishDao: DishDao): LocalDishDataSource {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = LocalDishDataSource(appExecutors, dishDao)
                }
            }
            return INSTANCE!!
        }
    }

    override fun insertData(data: Dish) {
    }

    override fun insertData(data: List<Dish>) {
        val insertRunnable = Runnable { dishDao.insertDishes(data) }
        appExecutors.diskIO.execute(insertRunnable)
    }

    override fun deleteData(data: Dish) {

    }

    override fun getComments(id: Int): LiveData<List<Comments>>? {
        return null
    }

    override fun getAllDishes(): LiveData<List<Dish>> {
        return dishDao.getAllDishes()
    }
}
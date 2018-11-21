package com.coppermobile.kotlinsamplemvvmlivedataroom.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MediatorLiveData
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.DataSource

class DishRepository private constructor(private val mRemoteDataSource: DataSource<Dish>, private val mLocalDataSource: DataSource<Dish>) : DataSource<Dish> {

    private lateinit var mediatorLiveData: MediatorLiveData<List<Dish>>

    companion object {

        private var INSTANCE: DishRepository? = null

        fun getInstance(tasksRemoteDataSource: DataSource<Dish>, tasksLocalDataSource: DataSource<Dish>): DishRepository {
            if (INSTANCE == null) {
                synchronized(DishRepository::class.java) {
                    INSTANCE = DishRepository(tasksRemoteDataSource, tasksLocalDataSource)
                }
            }
            return INSTANCE!!
        }
    }

    override fun insertData(data: Dish) {

    }

    override fun insertData(data: List<Dish>) {

    }

    override fun deleteData(data: Dish) {

    }

    override fun getComments(id: Int): LiveData<List<Comments>>? {
        return null
    }

    override fun getAllDishes(): LiveData<List<Dish>> {
        mediatorLiveData = MediatorLiveData()
        val allDishesFromDao = mLocalDataSource.getAllDishes()
        mediatorLiveData.addSource(allDishesFromDao) {
            mediatorLiveData.removeSource(allDishesFromDao)
            if (allDishesFromDao.value != null) {
                if (shouldFetch(allDishesFromDao.value))
                    fetchFromNetwork(allDishesFromDao)
                else
                    mediatorLiveData.addSource(allDishesFromDao) { t -> mediatorLiveData.value = t }
            }
        }
        return mediatorLiveData
    }

    private fun shouldFetch(dishList: List<Dish>?): Boolean {
        return dishList?.isEmpty() ?: false
    }

    private fun fetchFromNetwork(data: LiveData<List<Dish>>) {

        val allDishes = mRemoteDataSource.getAllDishes()

        mediatorLiveData.addSource(allDishes) { dishList ->
            mediatorLiveData.removeSource(allDishes)
            mediatorLiveData.removeSource(data)
            if (dishList != null) {
                mLocalDataSource.insertData(dishList)
                mediatorLiveData.addSource(mLocalDataSource.getAllDishes()) { finalDishListFromDao -> mediatorLiveData.setValue(finalDishListFromDao) }
            }
        }
    }
}
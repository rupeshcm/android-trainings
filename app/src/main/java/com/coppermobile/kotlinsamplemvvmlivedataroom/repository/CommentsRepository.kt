package com.coppermobile.kotlinsamplemvvmlivedataroom.repository

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.DataSource

class CommentsRepository private constructor(private val mLocalDataSource: DataSource<Comments>) : DataSource<Comments> {

    companion object {

        private var INSTANCE: CommentsRepository? = null
        fun getInstance(tasksLocalDataSource: DataSource<Comments>): CommentsRepository {
            if(INSTANCE==null){
                synchronized(this){
                    INSTANCE= CommentsRepository(tasksLocalDataSource)
                }
            }
            return INSTANCE!!
        }
    }

    override fun insertData(data: Comments) {
        mLocalDataSource.insertData(data)
    }

    override fun insertData(data: List<Comments>) {
    }

    override fun deleteData(data: Comments) {
        mLocalDataSource.deleteData(data)
    }

    override fun getComments(id: Int): LiveData<List<Comments>>? {
        return mLocalDataSource.getComments(id)
    }

    override fun getAllDishes(): LiveData<List<Dish>> {
        return MutableLiveData()
    }
}
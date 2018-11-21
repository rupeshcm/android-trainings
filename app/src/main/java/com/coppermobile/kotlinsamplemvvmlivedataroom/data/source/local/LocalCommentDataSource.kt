package com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.lifecycle.MutableLiveData
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.DataSource
import com.coppermobile.kotlinsamplemvvmlivedataroom.utils.AppExecutors

class LocalCommentDataSource private constructor(private val appExecutors: AppExecutors, private val commentsDao: CommentsDao) : DataSource<Comments> {

    companion object {

        private var INSTANCE: LocalCommentDataSource? = null

        fun getInstance(appExecutors: AppExecutors, commentsDao: CommentsDao): LocalCommentDataSource {
            if (INSTANCE == null) {
                synchronized(this) {
                    INSTANCE = LocalCommentDataSource(appExecutors, commentsDao)
                }
            }
            return INSTANCE!!
        }
    }

    override fun insertData(data: Comments) {
        val insertRunnable = Runnable { commentsDao.insertComment(data) }
        appExecutors.diskIO.execute(insertRunnable)
    }

    override fun insertData(data: List<Comments>) {
    }

    override fun deleteData(data: Comments) {
        val deleteRunnable = Runnable { commentsDao.deleteComment(data) }
        appExecutors.diskIO.execute(deleteRunnable)
    }

    override fun getComments(id: Int): LiveData<List<Comments>>? {
        return commentsDao.getComments(id)
    }

    override fun getAllDishes(): LiveData<List<Dish>> {
        return MutableLiveData()
    }
}
package com.coppermobile.kotlinsamplemvvmlivedataroom.utils

import android.content.Context
import android.support.annotation.NonNull
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.local.AppDatabase
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.local.LocalCommentDataSource
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.local.LocalDishDataSource
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.remote.RemoteDishDataSource
import com.coppermobile.kotlinsamplemvvmlivedataroom.repository.CommentsRepository
import com.coppermobile.kotlinsamplemvvmlivedataroom.repository.DishRepository

object Injection {

    fun provideDishRepository(@NonNull context: Context): DishRepository {
        val appDataBase = AppDatabase.getInstance(context)
        return DishRepository.getInstance(RemoteDishDataSource.instance, LocalDishDataSource.getInstance(AppExecutors(), appDataBase.getDishDao()))
    }

    fun provideCommentsRepository(@NonNull context: Context): CommentsRepository {
        val appDataBase = AppDatabase.getInstance(context)
        return CommentsRepository.getInstance(LocalCommentDataSource.getInstance(AppExecutors(), appDataBase.getCommentsDao()))
    }
}
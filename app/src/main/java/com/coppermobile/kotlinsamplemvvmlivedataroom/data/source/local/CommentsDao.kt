package com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.local

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments

@Dao
interface CommentsDao {

    @Insert
    fun insertComment(comments: Comments)

    @Delete
    fun deleteComment(comments: Comments)

    @Query("select * from comments_table where dish_id=:dishId")
    fun getComments(dishId: Int): LiveData<List<Comments>>
}
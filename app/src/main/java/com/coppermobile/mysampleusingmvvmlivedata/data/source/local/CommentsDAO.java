package com.coppermobile.mysampleusingmvvmlivedata.data.source.local;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.coppermobile.mysampleusingmvvmlivedata.data.Comments;

import java.util.List;

@Dao
public interface CommentsDAO {

    @Insert
    void insertComments(Comments Comment);

    @Query("SELECT * FROM comments_table WHERE dish_id=:dishId ORDER BY comment_time DESC;")
    LiveData<List<Comments>> getComments(int dishId);

    @Delete
    void deleteComments(Comments comments);
}
package com.coppermobile.mysampleusingmvvmlivedata.repository;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.coppermobile.mysampleusingmvvmlivedata.data.Comments;
import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;
import com.coppermobile.mysampleusingmvvmlivedata.data.source.DataSource;

import java.util.List;

public class CommentsRepository implements DataSource<Comments> {

    private volatile static CommentsRepository INSTANCE = null;

    private DataSource<Comments> mLocalDataSource;

    private CommentsRepository(@NonNull DataSource<Comments> mLocalDataSource) {
        this.mLocalDataSource = mLocalDataSource;
    }

    public static CommentsRepository getInstance(DataSource<Comments> tasksLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (CommentsRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new CommentsRepository(tasksLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void insertData(Comments comment) {
        mLocalDataSource.insertData(comment);
    }

    @Override
    public void insertData(List<Comments> commentsList) {

    }

    @Override
    public void deleteData(Comments comment) {
        mLocalDataSource.deleteData(comment);
    }

    @Override
    public LiveData<List<Comments>> getComments(int id) {
        return mLocalDataSource.getComments(id);
    }

    @Override
    public LiveData<List<Dish>> getAllDishes() {
        return null;
    }
}
package com.coppermobile.mysamplemvvmdatabindinglivedata.data.source.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Comments;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.source.DataSource;
import com.coppermobile.mysamplemvvmdatabindinglivedata.utils.AppExecutors;

import java.util.List;

public class LocalCommentsDataSource implements DataSource<Comments> {

    private static volatile LocalCommentsDataSource INSTANCE;

    private CommentsDAO commentsDAO;

    private AppExecutors appExecutors;

    private LocalCommentsDataSource(@NonNull AppExecutors appExecutors, @NonNull CommentsDAO commentsDAO) {
        this.appExecutors = appExecutors;
        this.commentsDAO = commentsDAO;
    }

    public static LocalCommentsDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull CommentsDAO commentsDAO) {
        if (INSTANCE == null) {
            synchronized (LocalCommentsDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalCommentsDataSource(appExecutors, commentsDAO);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void insertData(Comments comment) {
        Runnable insertRunnable = () -> commentsDAO.insertComments(comment);
        appExecutors.getDiskIO().execute(insertRunnable);
    }

    @Override
    public void insertData(List<Comments> commentsList) {
        //no use here
    }

    @Override
    public void deleteData(Comments comment) {
        Runnable deleteRunnable = () -> commentsDAO.deleteComments(comment);
        appExecutors.getDiskIO().execute(deleteRunnable);
    }

    @Override
    public LiveData<List<Comments>> getComments(int id) {
        return commentsDAO.getComments(id);
    }

    @Override
    public LiveData<List<Dish>> getAllDishes() {
        return null;
    }
}

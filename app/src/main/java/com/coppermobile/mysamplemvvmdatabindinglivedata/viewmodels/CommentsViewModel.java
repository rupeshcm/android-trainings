package com.coppermobile.mysamplemvvmdatabindinglivedata.viewmodels;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Comments;
import com.coppermobile.mysamplemvvmdatabindinglivedata.repository.CommentsRepository;

import java.util.List;

public class CommentsViewModel extends AndroidViewModel {

    private final CommentsRepository commentsRepository;

    public CommentsViewModel(@NonNull Application application, CommentsRepository commentsRepository) {
        super(application);
        this.commentsRepository = commentsRepository;
    }

    public LiveData<List<Comments>> getAllComments(int dishId) {
        return commentsRepository.getComments(dishId);
    }

    public void deleteComment(Comments comments){
        commentsRepository.deleteData(comments);
    }

    public void insertComment(Comments comments){
        commentsRepository.insertData(comments);
    }
}
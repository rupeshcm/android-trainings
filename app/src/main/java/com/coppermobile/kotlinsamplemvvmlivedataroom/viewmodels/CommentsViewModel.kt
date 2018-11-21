package com.coppermobile.kotlinsamplemvvmlivedataroom.viewmodels

import android.app.Application
import android.arch.lifecycle.AndroidViewModel
import android.arch.lifecycle.LiveData
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.repository.CommentsRepository

class CommentsViewModel(application: Application, private val commentsRepository: CommentsRepository) : AndroidViewModel(application) {

    fun deleteComment(comment: Comments) {
        commentsRepository.deleteData(comment)
    }

    fun insertComment(comment: Comments) {
        commentsRepository.insertData(comment)
    }

    fun getComments(dishId:Int): LiveData<List<Comments>>? {
       return commentsRepository.getComments(dishId)
    }
}
package com.coppermobile.kotlinsamplemvvmlivedataroom.utils

import android.view.View

import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments

interface IShowPopUpListener {

    fun showPopupMenu(comments: Comments, position: Int, view: View)
}
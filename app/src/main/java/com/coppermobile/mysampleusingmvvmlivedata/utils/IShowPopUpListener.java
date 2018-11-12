package com.coppermobile.mysampleusingmvvmlivedata.utils;

import android.view.View;

import com.coppermobile.mysampleusingmvvmlivedata.data.Comments;

public interface IShowPopUpListener {

    void showPopupMenu(Comments comments, int position, View view);
}

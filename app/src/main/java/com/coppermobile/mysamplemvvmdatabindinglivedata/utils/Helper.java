package com.coppermobile.mysamplemvvmdatabindinglivedata.utils;

import android.databinding.BindingAdapter;
import android.text.format.DateFormat;
import android.widget.ImageView;

import com.bumptech.glide.Glide;

import java.util.Date;

public class Helper {

    private Helper() {
    }

    public static String formatDate() {
        return (String) DateFormat.format("MMM d, yy ", new Date());
    }

    @BindingAdapter({"imageURL"})
    public static void loadImage(ImageView view, String imageUrl) {
        Glide.with(view.getContext()).load(imageUrl).into(view);
    }
}
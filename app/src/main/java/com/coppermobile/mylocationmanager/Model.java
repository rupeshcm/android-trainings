package com.coppermobile.mylocationmanager;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import java.util.Arrays;
import java.util.List;

public class Model {

    private static Integer[] images = {R.drawable.apple, R.drawable.banana, R.drawable.cherry, R.drawable.grapes, R.drawable.guava,
            R.drawable.mango, R.drawable.orange, R.drawable.watermelon, R.drawable.strawberry, R.drawable.pomegranate};

    public static LiveData<List<Integer>> getFruits() {
        MutableLiveData<List<Integer>> mutableLiveData = new MutableLiveData<>();
        mutableLiveData.setValue(Arrays.asList(images));
        return mutableLiveData;
    }
}
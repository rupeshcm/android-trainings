package com.coppermobile.mylocationmanager;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.os.Handler;
import android.support.annotation.NonNull;

import java.util.List;

public class MyViewModel extends AndroidViewModel {

    private List<Integer> imageList;
    private MutableLiveData<Integer> chunkedData = new MutableLiveData<>();
    private int i = 0;

    public MyViewModel(@NonNull Application application) {
        super(application);

        LiveData<List<Integer>> fruits = Model.getFruits();

        Observer<List<Integer>> observer = integers -> {
            imageList = integers;
            forLoop();
        };

        fruits.observeForever(observer);
    }

    public MutableLiveData<Integer> getFruits() {
        return chunkedData;
    }

    private void forLoop() {
        chunkedData.setValue(imageList.get(i));
        fiveSecondsDelay();
    }

    private void fiveSecondsDelay() {
        try {
            new Handler().postDelayed(() -> {
                i++;
                forLoop();
                validation();
            }, 5000);
        } catch (Exception ignored) {
        }
    }

    private void validation() {
        if (i == 9) i = -1;
    }
}

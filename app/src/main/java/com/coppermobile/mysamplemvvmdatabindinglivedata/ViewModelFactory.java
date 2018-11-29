package com.coppermobile.mysamplemvvmdatabindinglivedata;

import android.app.Application;
import android.arch.lifecycle.ViewModel;
import android.arch.lifecycle.ViewModelProvider;
import android.support.annotation.NonNull;

import com.coppermobile.mysamplemvvmdatabindinglivedata.utils.Injection;
import com.coppermobile.mysamplemvvmdatabindinglivedata.viewmodels.CommentsViewModel;
import com.coppermobile.mysamplemvvmdatabindinglivedata.viewmodels.DishViewModel;

public class ViewModelFactory extends ViewModelProvider.NewInstanceFactory {

    private static ViewModelFactory INSTANCE;
    private final Application mApplication;

    public static ViewModelFactory getInstance(Application application) {

        if (INSTANCE == null) {
            synchronized (ViewModelFactory.class) {
                if (INSTANCE == null) {
                    INSTANCE = new ViewModelFactory(application);
                }
            }
        }
        return INSTANCE;
    }

    private ViewModelFactory(Application application) {
        mApplication = application;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if (modelClass.isAssignableFrom(DishViewModel.class)) {
            //noinspection unchecked
            return (T) new DishViewModel(mApplication, Injection.provideDishRepository(mApplication));
        } else if (modelClass.isAssignableFrom(CommentsViewModel.class)) {
            //noinspection unchecked
            return (T) new CommentsViewModel(mApplication,Injection.provideCommentsRepository(mApplication));
        }
        throw new IllegalArgumentException("Unknown ViewModel class: " + modelClass.getName());
    }
}
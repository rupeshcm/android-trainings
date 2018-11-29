package com.coppermobile.mysamplemvvmdatabindinglivedata.repository;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Comments;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.source.DataSource;

import java.util.List;

public class DishRepository implements DataSource<Dish> {

    private static DishRepository INSTANCE = null;
    private DataSource<Dish> mRemoteDataSource;
    private DataSource<Dish> mLocalDataSource;
    private MediatorLiveData<List<Dish>> mediatorLiveData;

    private DishRepository(@NonNull DataSource<Dish> mRemoteDataSource, @NonNull DataSource<Dish> mLocalDataSource) {
        this.mRemoteDataSource = mRemoteDataSource;
        this.mLocalDataSource = mLocalDataSource;
    }

    public static DishRepository getInstance(DataSource<Dish> tasksRemoteDataSource, DataSource<Dish> tasksLocalDataSource) {
        if (INSTANCE == null) {
            synchronized (DishRepository.class) {
                if (INSTANCE == null) {
                    INSTANCE = new DishRepository(tasksRemoteDataSource, tasksLocalDataSource);
                }
            }
        }
        return INSTANCE;
    }

    @Override
    public void insertData(Dish data) {
    }

    @Override
    public void insertData(List<Dish> data) {
    }

    @Override
    public void deleteData(Dish data) {
    }

    @Override
    public LiveData<List<Comments>> getComments(int id) {
        return null;
    }

    @Override
    public LiveData<List<Dish>> getAllDishes() {
        mediatorLiveData = new MediatorLiveData<>();
        LiveData<List<Dish>> allDishesFromDao = mLocalDataSource.getAllDishes();
        mediatorLiveData.addSource(allDishesFromDao, new Observer<List<Dish>>() {
            @Override
            public void onChanged(@Nullable List<Dish> dishList) {
                mediatorLiveData.removeSource(allDishesFromDao);
                if (allDishesFromDao.getValue() != null) {
                    if (DishRepository.this.shouldFetch(allDishesFromDao.getValue())) {
                        DishRepository.this.fetchFromNetwork(allDishesFromDao);
                    } else {
                        mediatorLiveData.addSource(allDishesFromDao, dishList1 -> mediatorLiveData.setValue(dishList1));
                    }
                }
            }
        });

        return mediatorLiveData;
    }

    private void fetchFromNetwork(LiveData<List<Dish>> data) {
        LiveData<List<Dish>> allDishes = mRemoteDataSource.getAllDishes();

        mediatorLiveData.addSource(data, dishList -> {
            //add loader
        });

        mediatorLiveData.addSource(allDishes, dishList -> {
            mediatorLiveData.removeSource(allDishes);
            mediatorLiveData.removeSource(data);
            if (dishList != null) {
                mLocalDataSource.insertData(dishList);
                mediatorLiveData.addSource(mLocalDataSource.getAllDishes(), finalDishListFromDao -> mediatorLiveData.setValue(finalDishListFromDao));
            } else {
                //error case handle here
            }
        });
    }

    private boolean shouldFetch(List<Dish> dishList) {
        return dishList == null || dishList.isEmpty();
    }
}
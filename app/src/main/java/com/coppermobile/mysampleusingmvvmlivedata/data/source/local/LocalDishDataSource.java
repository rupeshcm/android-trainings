package com.coppermobile.mysampleusingmvvmlivedata.data.source.local;

import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import com.coppermobile.mysampleusingmvvmlivedata.data.Comments;
import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;
import com.coppermobile.mysampleusingmvvmlivedata.data.source.DataSource;
import com.coppermobile.mysampleusingmvvmlivedata.utils.AppExecutors;

import java.util.List;


public class LocalDishDataSource implements DataSource<Dish> {

    private static volatile LocalDishDataSource INSTANCE;
    private DishDAO dishDAO;
    private AppExecutors appExecutors;

    private LocalDishDataSource(@NonNull AppExecutors appExecutors, @NonNull DishDAO dishDAO) {
        this.appExecutors = appExecutors;
        this.dishDAO = dishDAO;
    }

    public static LocalDishDataSource getInstance(@NonNull AppExecutors appExecutors, @NonNull DishDAO dishDAO) {
        if (INSTANCE == null) {
            synchronized (LocalDishDataSource.class) {
                if (INSTANCE == null) {
                    INSTANCE = new LocalDishDataSource(appExecutors, dishDAO);
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
        Runnable insertRunnable = () -> dishDAO.insertDishes(data);
        appExecutors.getDiskIO().execute(insertRunnable);
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
        return dishDAO.getAllDishes();
    }
}
package com.coppermobile.mysampleusingmvvmlivedata.data.source.remote;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.coppermobile.mysampleusingmvvmlivedata.data.Comments;
import com.coppermobile.mysampleusingmvvmlivedata.data.Dish;
import com.coppermobile.mysampleusingmvvmlivedata.data.source.DataSource;
import com.coppermobile.mysampleusingmvvmlivedata.repository.DishAPIInterface;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RemoteDishDataSource implements DataSource<Dish> {

    private static final String URL = "https://api.androidhive.info/";
    private static DishAPIInterface dishAPIInterface;
    private static RemoteDishDataSource INSTANCE;

    private RemoteDishDataSource() {
    }

    public synchronized static RemoteDishDataSource getInstance() {
        Gson gson = new GsonBuilder().setLenient().create();
        Retrofit retrofit = new Retrofit.Builder().baseUrl(URL).addConverterFactory(GsonConverterFactory.create(gson)).build();
        dishAPIInterface = retrofit.create(DishAPIInterface.class);

        if (INSTANCE == null) {
            INSTANCE = new RemoteDishDataSource();
        }
        return INSTANCE;
    }

    @Override
    public void insertData(Dish data) {
        //No use here
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
        final MutableLiveData<List<Dish>> mutableLiveData = new MutableLiveData<>();
        dishAPIInterface.getDishes().enqueue(new Callback<List<DishResponse>>() {
            @Override
            public void onResponse(@NonNull Call<List<DishResponse>> call, @NonNull Response<List<DishResponse>> response) {
                if (response.body() != null) {
                    List<DishResponse> dishResponseList = response.body();
                    if (dishResponseList != null) {
                        List<Dish> dishList = new ArrayList<>();
                        for (int i = 0; i < dishResponseList.size(); i++) {
                            Dish dish = new Dish();
                            dish.setDishId(dishResponseList.get(i).getId());
                            dish.setDishImage(dishResponseList.get(i).getThumbnail());
                            dish.setDishName(dishResponseList.get(i).getName());
                            dish.setDishDescription(dishResponseList.get(i).getDescription());
                            dish.setDishPrice(dishResponseList.get(i).getPrice());
                            dishList.add(dish);
                        }
                        mutableLiveData.setValue(dishList);
                    }
                }
            }

            @Override
            public void onFailure(@NonNull Call<List<DishResponse>> call, @NonNull Throwable t) {
                mutableLiveData.setValue(null);
            }
        });
        return mutableLiveData;
    }
}
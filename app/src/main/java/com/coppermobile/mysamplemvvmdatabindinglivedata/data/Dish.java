package com.coppermobile.mysamplemvvmdatabindinglivedata.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

import java.io.Serializable;

@Entity(tableName = "dish")
public class Dish implements Serializable {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private int id;

    @ColumnInfo(name = "dish_id")
    private int dishId;

    @ColumnInfo(name = "dish_image")
    private String dishImage;

    @ColumnInfo(name = "dish_name")
    private String dishName;

    @ColumnInfo(name = "dish_description")
    private String dishDescription;

    @ColumnInfo(name = "dish_price")
    private int dishPrice;

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public void setDishDescription(String dishDescription) {
        this.dishDescription = dishDescription;
    }

    public void setDishPrice(int dishPrice) {
        this.dishPrice = dishPrice;
    }

    public int getDishId() {
        return dishId;
    }

    public String getDishImage() {
        return dishImage;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDishDescription() {
        return dishDescription;
    }

    public int getDishPrice() {
        return dishPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
package com.coppermobile.mysampleusingmvvmlivedata.data.source.remote;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class DishResponse implements Serializable{

    @SerializedName("thumbnail")
    private String thumbnail;

    @SerializedName("price")
    private int price;

    @SerializedName("name")
    private String name;

    @SerializedName("description")
    private String description;

    @SerializedName("id")
    private int id;

    public String getThumbnail() {
        return thumbnail;
    }

    public int getPrice() {
        return price;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getId() {
        return id;
    }
}
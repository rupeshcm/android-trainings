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

    public void setThumbnail(String thumbnail) {
        this.thumbnail = thumbnail;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }
}
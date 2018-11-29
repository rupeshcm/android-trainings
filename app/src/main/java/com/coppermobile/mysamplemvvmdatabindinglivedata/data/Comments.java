package com.coppermobile.mysamplemvvmdatabindinglivedata.data;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

@Entity(tableName = "comments")
public class Comments {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    private int id;

    @ColumnInfo(name = "comment_name")
    private String comments;

    @ColumnInfo(name = "comment_time")
    private String date;

    @ColumnInfo(name = "dish_id")
    private int dishID;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getDishID() {
        return dishID;
    }

    public void setDishID(int dishID) {
        this.dishID = dishID;
    }
}
package com.coppermobile.kotlinsamplemvvmlivedataroom.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import android.support.annotation.NonNull
import java.io.Serializable

@Entity(tableName = "dish_table")
class Dish : Serializable {

    @NonNull
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    var id: Int = -1

    @ColumnInfo(name = "dish_image")
    var dishImage: String? = null

    @ColumnInfo(name = "dish_name")
    var dishName: String? = null

    @ColumnInfo(name = "dish_description")
    var dishDescription: String? = null

    @ColumnInfo(name = "dish_price")
    var dishPrice: Int = -1
}
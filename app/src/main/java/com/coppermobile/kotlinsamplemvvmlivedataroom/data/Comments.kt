package com.coppermobile.kotlinsamplemvvmlivedataroom.data

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

@Entity(tableName = "comments_table")
class Comments {

    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "comment_id")
    var id: Int = 0

    @ColumnInfo(name = "comment_name")
    var comments: String? = null

    @ColumnInfo(name = "comment_time")
    var date: String? = null

    @ColumnInfo(name = "dish_id")
    var dishId: Int = 0
}
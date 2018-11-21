package com.coppermobile.kotlinsamplemvvmlivedataroom.data.source.local

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import android.util.Log
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Comments
import com.coppermobile.kotlinsamplemvvmlivedataroom.data.Dish

@Database(entities = [Dish::class, Comments::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getDishDao(): DishDao
    abstract fun getCommentsDao(): CommentsDao

    companion object {

        private var instance: AppDatabase? = null
        private const val DATABASE_NAME = "myDb"

        fun getInstance(context: Context): AppDatabase {
            if (instance == null) {
                synchronized(this) {}
                try {
                    instance = Room.databaseBuilder(context.applicationContext, AppDatabase::class.java, DATABASE_NAME).build()
                } catch (ignored: Exception) {
                    Log.v("AppDatabase", ignored.toString())
                }
            }
            return instance!!
        }
    }
}
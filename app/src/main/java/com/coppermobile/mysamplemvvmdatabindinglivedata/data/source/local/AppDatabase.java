package com.coppermobile.mysamplemvvmdatabindinglivedata.data.source.local;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Comments;
import com.coppermobile.mysamplemvvmdatabindinglivedata.data.Dish;

@Database(entities = {Comments.class, Dish.class}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {

    private static AppDatabase instance;
    private static final String COMMENT_DATABASE_NAME = "comments_db";

    public abstract CommentsDAO commentsDoaMethod();
    public abstract DishDAO dishDaoMethod();

    public static AppDatabase getInstance(Context context) {
        if (instance == null) {
            try {
                instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, COMMENT_DATABASE_NAME).build();
            } catch (Exception ignored) {
            }
        }
        return instance;
    }
}
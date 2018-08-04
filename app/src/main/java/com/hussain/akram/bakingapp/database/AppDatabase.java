package com.hussain.akram.bakingapp.database;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.TypeConverter;
import android.arch.persistence.room.TypeConverters;
import android.content.Context;

import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.util.AppConstants;


@Database(entities = {Recipe.class}, version = 3, exportSchema = false)
@TypeConverters({IngredientTypeConverter.class,StepsTypeConverter.class})
public abstract class AppDatabase extends RoomDatabase{

    private static final Object LOCK = new Object();
    private static final String DATABASE_NAME = "recipeList";
    private static AppDatabase sInstance;

        public static AppDatabase getInstance(Context context) {
        if (sInstance == null) {
            synchronized (LOCK) {
                sInstance = Room.databaseBuilder(context.getApplicationContext(),
                        AppDatabase.class,AppDatabase.DATABASE_NAME)
                        .allowMainThreadQueries()
                        .build();
            }
        }
        return sInstance;
    }

    public abstract RecipeDAO recipeDAO();
}

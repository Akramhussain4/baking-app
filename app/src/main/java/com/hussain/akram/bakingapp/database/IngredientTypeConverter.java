package com.hussain.akram.bakingapp.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hussain.akram.bakingapp.model.Ingredients;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class IngredientTypeConverter {

    @TypeConverter
    public static List<Ingredients> stringToSomeObjectList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Ingredients>>() {
        }.getType();


        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Ingredients> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }
}

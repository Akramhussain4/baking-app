package com.hussain.akram.bakingapp.database;

import android.arch.persistence.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hussain.akram.bakingapp.model.Ingredients;
import com.hussain.akram.bakingapp.model.Steps;

import java.lang.reflect.Type;
import java.util.Collections;
import java.util.List;

public class StepsTypeConverter {

    @TypeConverter
    public static List<Steps> stringToSomeObjectList(String data) {
        Gson gson = new Gson();
        if (data == null) {
            return Collections.emptyList();
        }

        Type listType = new TypeToken<List<Steps>>() {
        }.getType();


        return gson.fromJson(data, listType);
    }

    @TypeConverter
    public static String someObjectListToString(List<Steps> someObjects) {
        Gson gson = new Gson();
        return gson.toJson(someObjects);
    }
}

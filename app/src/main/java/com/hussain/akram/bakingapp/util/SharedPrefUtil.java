/*
 * Copyright 2018 Dionysios Karatzas
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.hussain.akram.bakingapp.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;
import com.hussain.akram.bakingapp.model.Recipe;


public class SharedPrefUtil {

    public static void saveRecipe(Context context, Recipe recipe) {
        SharedPreferences.Editor prefs = context.getSharedPreferences(AppConstants.RECIPE_PREF, Context.MODE_PRIVATE).edit();
        Gson gson = new Gson();
        String json = gson.toJson(recipe);
        prefs.putString(AppConstants.RECIPE_PREF_KEY, json);
        prefs.apply();
    }

    public static Recipe loadRecipe(Context context) {
        SharedPreferences prefs = context.getSharedPreferences(AppConstants.RECIPE_PREF, Context.MODE_PRIVATE);
        Gson gson = new Gson();
        String json = prefs.getString(AppConstants.RECIPE_PREF_KEY, "");
        Recipe recipe = gson.fromJson(json, Recipe.class);
        return "".equals(json) ? null : recipe;
    }
}

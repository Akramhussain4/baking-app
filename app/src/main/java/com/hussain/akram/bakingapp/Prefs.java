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

package com.hussain.akram.bakingapp;

import android.content.Context;

import com.hussain.akram.bakingapp.database.AppDatabase;
import com.hussain.akram.bakingapp.model.Recipe;

import java.util.List;

public class Prefs {

    public static void saveRecipe(Context context, Recipe recipe) {
        AppDatabase.getInstance(context).recipeDAO().inserRecipe(recipe);
    }

    public static List<Recipe> loadRecipe(Context context) {

        List<Recipe> recipe = AppDatabase.getInstance(context).recipeDAO().loadAllRecipes();

        return recipe;
    }
}

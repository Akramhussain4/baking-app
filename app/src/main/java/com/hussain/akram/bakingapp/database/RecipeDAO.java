package com.hussain.akram.bakingapp.database;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import com.hussain.akram.bakingapp.model.Recipe;

import java.util.List;

@Dao
public interface RecipeDAO {

    @Query("SELECT * FROM recipe")
    List<Recipe> loadAllRecipes();

    @Insert
    void inserRecipe(Recipe recipe);

    @Query("DELETE FROM recipe WHERE recipeId = :id")
    void deleteRecipe(String id);
}

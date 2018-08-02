package com.hussain.akram.bakingapp.activity;

import android.content.Intent;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;


import com.hussain.akram.bakingapp.NetworkInterface.RecipeInterface;
import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.adapter.RecipeAdapter;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.util.AppConstants;
import com.hussain.akram.bakingapp.util.NetworkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    private static final String TAG = MainActivity.class.getName();

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;
    private List<Recipe> recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        GridLayoutManager layoutManager = new GridLayoutManager(this,getSpan());
        recyclerView.setLayoutManager(layoutManager);
        checkNetwork();
    }

    public void checkNetwork() {
        if (NetworkUtil.isNetworkAvailable(this)) {
            getRecipes();
        } else {
            NetworkUtil.showNetworkAlert(this);
        }
    }

    private int getSpan() {
        if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE) {
            return 3;
        }
        return 1;
    }


    private void getRecipes() {
        final RecipeInterface recipeInterface = NetworkUtil.buildUrl().create(RecipeInterface.class);
        Call<List<Recipe>> getRecipe = recipeInterface.getRecipe();
        getRecipe.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                recipe = response.body();
                RecipeAdapter adapter = new RecipeAdapter(recipe, getApplicationContext(),MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {
                Log.d(TAG, "onFailure()", t);
            }
        });
    }

    @Override
    public void recipeIndex(int index) {
        Intent intent = new Intent(getApplicationContext(), RecipeActivity.class);
        Bundle bundle = new Bundle();
        bundle.putParcelable(AppConstants.RECIPE_PARCELABLE, recipe.get(index));
        intent.putExtra(AppConstants.RECIPE_BUNDLE, bundle);
        startActivity(intent);
    }
}

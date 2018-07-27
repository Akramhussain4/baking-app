package com.hussain.akram.bakingapp.activity;

import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.hussain.akram.bakingapp.NetworkInterface.RecipeInterface;
import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.adapter.RecipeAdapter;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.util.NetworkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity implements RecipeAdapter.RecipeClickListener {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final RecipeInterface recipeInterface = NetworkUtil.buildUrl().create(RecipeInterface.class);
        Call<List<Recipe>> getRecipe = recipeInterface.getRecipe();
        getRecipe.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(@NonNull Call<List<Recipe>> call, @NonNull Response<List<Recipe>> response) {
                List<Recipe> recipe = response.body();
                RecipeAdapter adapter = new RecipeAdapter(recipe,MainActivity.this);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(@NonNull Call<List<Recipe>> call, @NonNull Throwable t) {

            }
        });
    }

    @Override
    public void recipeIndex(int index) {

    }
}

package com.hussain.akram.bakingapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hussain.akram.bakingapp.NetworkInterface.RecipiesInterface;
import com.hussain.akram.bakingapp.adapter.RecipeAdapter;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.util.NetworkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.recyclerView)
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        final RecipiesInterface recipiesInterface = NetworkUtil.buildUrl().create(RecipiesInterface.class);
        Call<List<Recipe>> getRecipe = recipiesInterface.getRecipe();
        getRecipe.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                List<Recipe> recipe = response.body();
                RecipeAdapter adapter = new RecipeAdapter(recipe);
                recyclerView.setAdapter(adapter);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {

            }
        });
    }
}

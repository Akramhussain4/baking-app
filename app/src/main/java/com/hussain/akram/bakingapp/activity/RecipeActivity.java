package com.hussain.akram.bakingapp.activity;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.fragment.RecipeIngredients;
import com.hussain.akram.bakingapp.util.AppConstants;
import com.hussain.akram.bakingapp.util.NetworkUtil;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        b = getIntent().getBundleExtra(AppConstants.RECIPE_BUNDLE);
        ButterKnife.bind(this);
        checkNetwork();
    }

    public void checkNetwork() {
        if (NetworkUtil.isNetworkAvailable(this)) {
            startFragment();
        } else {
            NetworkUtil.showNetworkAlert(this);
        }
    }

    private void startFragment(){
        RecipeIngredients recipeIngredients = new RecipeIngredients();
        recipeIngredients.setArguments(b);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, recipeIngredients).commit();
    }

}

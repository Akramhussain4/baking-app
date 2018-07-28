package com.hussain.akram.bakingapp.activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.fragment.RecipeDescription;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.util.AppConstants;
import com.hussain.akram.bakingapp.util.NetworkUtil;

public class RecipeActivity extends AppCompatActivity {

    private Bundle b;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        b = getIntent().getBundleExtra(AppConstants.RECIPE_BUNDLE);
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
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        RecipeDescription recipeDescription = new RecipeDescription();
        recipeDescription.setArguments(b);
        fragmentTransaction.add(R.id.fragmentContainer, recipeDescription);
        fragmentTransaction.commit();
    }

}

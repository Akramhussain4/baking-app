package com.hussain.akram.bakingapp.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.fragment.RecipeIngredients;
import com.hussain.akram.bakingapp.fragment.RecipeInstruction;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.model.Steps;
import com.hussain.akram.bakingapp.util.AppConstants;
import com.hussain.akram.bakingapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    private Bundle b;
    private boolean twoPane;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        b = getIntent().getBundleExtra(AppConstants.RECIPE_BUNDLE);
        ButterKnife.bind(this);
        if (findViewById(R.id.twoPaneLayout) != null) {
            twoPane = true;
            Recipe recipe = b.getParcelable(AppConstants.RECIPE_PARCELABLE);
            List<Steps> stepsList = recipe.getSteps();
            RecipeInstruction recipeInstruction = new RecipeInstruction();
            Bundle b = new Bundle();
            b.putParcelableArrayList(AppConstants.STEPS_BUNDLE, (ArrayList<? extends Parcelable>) stepsList);
            b.putInt(AppConstants.INDEX, 0);
            recipeInstruction.setArguments(b);
            FragmentManager fragmentManager = getSupportFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.instructionFragment, recipeInstruction)
                    .commit();

        } else {
            twoPane = false;
        }
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
        b.putBoolean(AppConstants.TWO_PANE,twoPane);
        recipeIngredients.setArguments(b);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.recipeFragment, recipeIngredients).commit();
    }

}

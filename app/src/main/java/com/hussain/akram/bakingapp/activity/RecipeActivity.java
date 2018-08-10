package com.hussain.akram.bakingapp.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.hussain.akram.bakingapp.R;

import com.hussain.akram.bakingapp.fragment.RecipeIngredients;
import com.hussain.akram.bakingapp.fragment.RecipeInstruction;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.model.Steps;
import com.hussain.akram.bakingapp.util.AppConstants;
import com.hussain.akram.bakingapp.util.NetworkUtil;
import com.hussain.akram.bakingapp.widget.AppWidgetService;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class RecipeActivity extends AppCompatActivity {

    private Bundle b;
    private boolean twoPane;
    private Recipe recipe;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe);
        b = getIntent().getBundleExtra(AppConstants.RECIPE_BUNDLE);
        recipe = b.getParcelable(AppConstants.RECIPE_PARCELABLE);
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.recipe_info, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.action_add_to_widget) {
            AppWidgetService.updateWidget(this, recipe);
            Toast.makeText(this,"Added Widget",Toast.LENGTH_SHORT).show();
            return true;
        } else
            return super.onOptionsItemSelected(item);
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

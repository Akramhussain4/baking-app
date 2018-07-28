package com.hussain.akram.bakingapp.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.model.Ingredients;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.util.AppConstants;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeDescription extends Fragment {

    @BindView(R.id.ingredients)
    TextView ingredient;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Recipe recipe = this.getArguments().getParcelable(AppConstants.RECIPE_PARCELABLE);
        List<Ingredients> ing = recipe.getIngredients();
        ingredient.setText(ing.get(0).getIngredient());
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_recipe_description, container, false);
        ButterKnife.bind(this,view);
        return view;
    }

}

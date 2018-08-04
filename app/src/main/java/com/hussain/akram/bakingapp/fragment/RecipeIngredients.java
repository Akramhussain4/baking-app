package com.hussain.akram.bakingapp.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.activity.InstructionsActivity;
import com.hussain.akram.bakingapp.adapter.StepsAdapter;
import com.hussain.akram.bakingapp.model.Ingredients;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.model.Steps;
import com.hussain.akram.bakingapp.util.AppConstants;
import com.hussain.akram.bakingapp.widget.AppWidgetService;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeIngredients extends Fragment implements StepsAdapter.StepsClickListener {

    @BindView(R.id.tvIngredients)
    TextView tvIngredients;
    @BindView(R.id.rvSteps)
    RecyclerView rvSteps;

    private boolean twoPane;
    private Recipe recipe;
    private List<Steps> stepsList;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_description, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (this.getArguments() != null) {
            recipe = this.getArguments().getParcelable(AppConstants.RECIPE_PARCELABLE);
            twoPane = this.getArguments().getBoolean(AppConstants.TWO_PANE);
            setAdapter();
            setIngredients();
        }
    }

    private void setIngredients() {
        List<Ingredients> ingredients = recipe.getIngredients();
        StringBuilder stringBuilder = new StringBuilder();
        for (Ingredients details : ingredients) {
            stringBuilder.append("* ").append(details.getIngredient())
                    .append(" - ").append(details.getQuantity()).append(" ")
                    .append(details.getMeasure()).append("\n");
        }
        tvIngredients.setText(stringBuilder);
    }

    private void setAdapter() {
        stepsList = recipe.getSteps();
        StepsAdapter adapter = new StepsAdapter(stepsList, getContext(), this);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        rvSteps.setLayoutManager(layoutManager);
        rvSteps.setAdapter(adapter);
    }

    @Override
    public void stepClickListener(int index) {
        if (twoPane) {
            RecipeInstruction recipeInstruction = new RecipeInstruction();
            Bundle b = new Bundle();
            b.putParcelableArrayList(AppConstants.STEPS_BUNDLE, (ArrayList<? extends Parcelable>) stepsList);
            b.putInt(AppConstants.INDEX, index);
            recipeInstruction.setArguments(b);
            FragmentManager fragmentManager = getFragmentManager();
            fragmentManager.beginTransaction()
                    .replace(R.id.instructionFragment, recipeInstruction)
                    .commit();
        } else {
            Intent intent = new Intent(getActivity(), InstructionsActivity.class);
            intent.putParcelableArrayListExtra(AppConstants.STEPS_PARCELABLE, (ArrayList<? extends Parcelable>) stepsList);
            intent.putExtra(AppConstants.INDEX, index);
            startActivity(intent);
        }
    }
}


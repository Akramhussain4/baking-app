package com.hussain.akram.bakingapp.activity;

import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.fragment.RecipeInstruction;
import com.hussain.akram.bakingapp.model.Steps;
import com.hussain.akram.bakingapp.util.AppConstants;
import com.hussain.akram.bakingapp.util.NetworkUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

public class InstructionsActivity extends AppCompatActivity {


    private List<Steps> stepsList;
    private int index;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ButterKnife.bind(this);
        stepsList = getIntent().getParcelableArrayListExtra(AppConstants.STEPS_PARCELABLE);
        index = getIntent().getIntExtra(AppConstants.INDEX, 0);
        checkNetwork();
    }

    public void checkNetwork() {
        if (NetworkUtil.isNetworkAvailable(this)) {
            startFragment();
        } else {
            NetworkUtil.showNetworkAlert(this);
        }
    }

    private void startFragment() {
        RecipeInstruction recipeInstruction = new RecipeInstruction();
        Bundle b = new Bundle();
        b.putParcelableArrayList(AppConstants.STEPS_BUNDLE, (ArrayList<? extends Parcelable>) stepsList);
        b.putInt(AppConstants.INDEX, index);
        recipeInstruction.setArguments(b);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.instructionFragment, recipeInstruction)
                .commit();
    }
}

package com.hussain.akram.bakingapp.activity;

import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayer;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.fragment.RecipeIngredients;
import com.hussain.akram.bakingapp.fragment.RecipeInstruction;
import com.hussain.akram.bakingapp.model.Steps;
import com.hussain.akram.bakingapp.util.AppConstants;
import com.hussain.akram.bakingapp.util.NetworkUtil;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InstructionsActivity extends AppCompatActivity {


    private Steps stepsList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_instructions);
        ButterKnife.bind(this);
        stepsList = getIntent().getParcelableExtra(AppConstants.STEPS_PARCELABLE);
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
        RecipeInstruction recipeInstruction = new RecipeInstruction();
        Bundle b = new Bundle();
        b.putParcelable(AppConstants.STEPS_BUNDLE,stepsList);
        recipeInstruction.setArguments(b);
        FragmentManager fragmentManager = getSupportFragmentManager();
        fragmentManager.beginTransaction()
                .add(R.id.fragmentContainer, recipeInstruction)
                .commit();
    }

}

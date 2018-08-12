package com.hussain.akram.bakingapp.fragment;

import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.model.Steps;
import com.hussain.akram.bakingapp.util.AppConstants;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RecipeInstruction extends Fragment {

    @BindView(R.id.steps)
    TextView tvSteps;
    @BindView(R.id.exoplayer)
    PlayerView mPlayerView;
    @BindView(R.id.arrow_forward)
    ImageView forwardArrow;
    @BindView(R.id.arrow_back)
    ImageView backwardArrow;

    private SimpleExoPlayer mExoPlayer;
    private List<Steps> steps;
    private int index;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe_instruction, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        loadData();
    }

    private void loadData() {
        if (this.getArguments() != null) {
            steps = getArguments().getParcelableArrayList(AppConstants.STEPS_BUNDLE);
            index = getArguments().getInt(AppConstants.INDEX, 0);
        }
        tvSteps.setText(steps.get(index).getDescription());
        Uri uri = Uri.parse(steps.get(index).getVideoURL());
        if (!Uri.EMPTY.equals(uri)) {
            initializePlayer(uri);
        }
    }

    private void initializePlayer(Uri uri) {
        if (mExoPlayer == null) {
            mPlayerView.setVisibility(View.VISIBLE);
            mExoPlayer = ExoPlayerFactory.newSimpleInstance(
                    new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            MediaSource mediaSource = buildMediaSource(uri);
            mExoPlayer.prepare(mediaSource, true, false);
            mExoPlayer.setPlayWhenReady(true);
            mPlayerView.setPlayer(mExoPlayer);
        }
    }

    private void releasePlayer() {
        if (mExoPlayer != null) {
            mExoPlayer.stop();
            mExoPlayer.release();
            mExoPlayer = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(
                new DefaultHttpDataSourceFactory("exoplayer")).
                createMediaSource(uri);
    }


    @OnClick(R.id.arrow_forward)
    public void nextStep() {
        int forwardIndex = index + 1;
        if (forwardIndex != steps.size() && forwardIndex < steps.size()) {
            RecipeInstruction recipeInstruction = new RecipeInstruction();
            Bundle b = new Bundle();
            b.putParcelableArrayList(AppConstants.STEPS_BUNDLE, (ArrayList<? extends Parcelable>) steps);
            b.putInt(AppConstants.INDEX, forwardIndex);
            recipeInstruction.setArguments(b);
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.instructionFragment, recipeInstruction)
                        .commit();
            }
        } else {
            forwardArrow.setVisibility(View.GONE);
        }
    }

    @OnClick(R.id.arrow_back)
    public void previousStep() {
        int backwardIndex = index - 1;
        if (!(backwardIndex < 0)) {
            RecipeInstruction recipeInstruction = new RecipeInstruction();
            Bundle b = new Bundle();
            b.putParcelableArrayList(AppConstants.STEPS_BUNDLE, (ArrayList<? extends Parcelable>) steps);
            b.putInt(AppConstants.INDEX, backwardIndex);
            recipeInstruction.setArguments(b);
            FragmentManager fragmentManager = getFragmentManager();
            if (fragmentManager != null) {
                fragmentManager.beginTransaction()
                        .replace(R.id.instructionFragment, recipeInstruction)
                        .commit();
            }
        } else {
            backwardArrow.setVisibility(View.GONE);
        }
    }
}

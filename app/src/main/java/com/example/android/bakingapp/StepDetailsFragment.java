package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;

import com.example.android.bakingapp.databinding.FragmentStepDetailsBinding;
import com.example.android.bakingapp.domain.RecipeStep;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.LoadControl;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

/**
 * Created by mateus on 18/06/17.
 */

public class StepDetailsFragment extends Fragment {

    private static final String BUNDLE_STEP = "bundle_step";
    private static final String BUNDLE_WINDOW_INDEX = "bundle_window_position";
    private static final String BUNDLE_POSITION = "bundle_position";
    private static final String LOG_TAG = "StepDetailsFragment";
    private FragmentStepDetailsBinding mBinding;

    private RecipeStep step;

    private SimpleExoPlayer player;

    public void setStep(RecipeStep step) {
        this.step = step;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentStepDetailsBinding.inflate(inflater, container, false);

        if(savedInstanceState != null){
            step = savedInstanceState.getParcelable(BUNDLE_STEP);
        }

        if(step != null && mBinding.tvStepInstructions != null) {
            mBinding.tvStepInstructions.setText(step.getDescription());
            mBinding.tvStepTitle.setText(step.getShortDescription());
        }

        return mBinding.getRoot();
    }

    @Override
    public void onStart() {
        super.onStart();
        initializePlayer();
    }

    @Override
    public void onResume() {
        super.onResume();
        initializePlayer();
    }

    @Override
    public void onStop() {
        super.onStop();
        releasePlayer();
    }

    @Override
    public void onPause() {
        super.onPause();
        releasePlayer();
    }

    private void initializePlayer(){
        if(step.getVideoUrl().isEmpty()){
            mBinding.exoVideoView.setVisibility(View.GONE);
            return;
        }
        if(player == null) {
            Uri uri = Uri.parse(step.getVideoUrl());
            String userAgent = Util.getUserAgent(getActivity(), "BakingApp");
            DefaultHttpDataSourceFactory dataSourceFactory = new DefaultHttpDataSourceFactory(userAgent, new DefaultBandwidthMeter());
            MediaSource mediaSource = new ExtractorMediaSource(uri, dataSourceFactory,
                    new DefaultExtractorsFactory(), null, null);

            TrackSelector selector = new DefaultTrackSelector();
            LoadControl control = new DefaultLoadControl();

            player = ExoPlayerFactory.newSimpleInstance(getActivity(), selector, control);
            mBinding.exoVideoView.setPlayer(player);
            player.prepare(mediaSource, true, false);
            player.setPlayWhenReady(true);

        }

    }

    private void releasePlayer(){
        if(player != null){
            player.release();
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_STEP, step);
        outState.putInt(BUNDLE_WINDOW_INDEX, player.getCurrentWindowIndex());
        outState.putLong(BUNDLE_POSITION, player.getCurrentPosition());
        Log.d(LOG_TAG, "Position: " + player.getCurrentWindowIndex() + "  " + player.getCurrentPosition());
        super.onSaveInstanceState(outState);
    }


}

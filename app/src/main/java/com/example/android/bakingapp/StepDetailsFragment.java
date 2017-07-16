package com.example.android.bakingapp;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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
import com.squareup.picasso.Picasso;

/**
 * Created by mateus on 18/06/17.
 */

public class StepDetailsFragment extends Fragment {

    private static final String BUNDLE_STEP = "bundle_step";
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
        Log.d(LOG_TAG, "onStart");
        super.onStart();
        initializePlayer();
        showImage();
    }

    @Override
    public void onResume() {
        Log.d(LOG_TAG, "onResume");
        super.onResume();
        initializePlayer();
        showImage();
    }

    @Override
    public void onStop() {
        Log.d(LOG_TAG, "onStop");
        super.onStop();
    }

    @Override
    public void onPause() {
        Log.d(LOG_TAG, "onPause");
        super.onPause();
        playerPause();
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
        super.onDestroy();
        releasePlayer();
    }

    public void playerPause(){
        if(player != null){
            player.setPlayWhenReady(false);
        }
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

        }else{
            player.setPlayWhenReady(true);
        }

    }

    private void releasePlayer(){
        if(player != null){
            player.release();
            player = null;
        }
    }


    @Override
    public void onSaveInstanceState(Bundle outState) {
        outState.putParcelable(BUNDLE_STEP, step);
        super.onSaveInstanceState(outState);
    }

    private void showImage(){
        if(this.step.getThumbnailUrl().isEmpty()){
            mBinding.ivStepThumbnail.setVisibility(View.GONE);
        }else{
            Picasso.with(getContext()).load(this.step.getThumbnailUrl()).into(mBinding.ivStepThumbnail);
        }
    }

}

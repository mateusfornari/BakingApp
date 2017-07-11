package com.example.android.bakingapp;

import android.content.res.Configuration;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;

import com.example.android.bakingapp.databinding.ActivityStepDetailsBinding;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.domain.RecipeStep;

public class StepDetailsActivity extends AppCompatActivity {

    private static final java.lang.String BUNDLE_POSITION = "bundle_position";
    private ActivityStepDetailsBinding mBinding;
    private Recipe mRecipe;
    private int stepPosition;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_step_details);

        mRecipe = getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);
        if(savedInstanceState != null){
            stepPosition = savedInstanceState.getInt(BUNDLE_POSITION);
        }else {
            stepPosition = getIntent().getIntExtra(RecipeDetailsActivity.EXTRA_STEP_POSITION, 0);
        }
        startStepFragment(stepPosition);

        updateButtons();

        if(mBinding.btNextStep != null) {
            mBinding.btNextStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startStepFragment(++stepPosition);
                    updateButtons();
                    fullScreen();
                }
            });
        }
        if(mBinding.btPreviousStep != null) {
            mBinding.btPreviousStep.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startStepFragment(--stepPosition);
                    updateButtons();
                    fullScreen();
                }
            });
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        fullScreen();
    }

    private void startStepFragment(int position){
        FragmentManager manager = getSupportFragmentManager();
        if(position >= 0) {
            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setStep(mRecipe.getSteps().get(position));
            manager.beginTransaction().replace(R.id.step_details_fragment, fragment).commit();
        }else{
            IngredientsFragment fragment = new IngredientsFragment();
            manager.beginTransaction().replace(R.id.step_details_fragment, fragment).commit();
        }
    }

    private void updateButtons(){
        if(mBinding.btNextStep != null && mBinding.btPreviousStep != null) {
            if (stepPosition >= 0) {
                mBinding.btPreviousStep.setVisibility(View.VISIBLE);
            } else {
                mBinding.btPreviousStep.setVisibility(View.INVISIBLE);
            }

            if (stepPosition < mRecipe.getSteps().size() - 1) {
                mBinding.btNextStep.setVisibility(View.VISIBLE);
            } else {
                mBinding.btNextStep.setVisibility(View.INVISIBLE);
            }
        }
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        outState.putInt(BUNDLE_POSITION, this.stepPosition);
        super.onSaveInstanceState(outState);
    }

    private void fullScreen(){
        if(getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            if(stepPosition >= 0) {
                RecipeStep step = mRecipe.getSteps().get(stepPosition);
                if (!step.getVideoUrl().isEmpty()) {
                    getWindow().addFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
                    mBinding.btNextStep.setVisibility(View.GONE);
                    mBinding.btPreviousStep.setVisibility(View.GONE);
                    getSupportActionBar().hide();
                    mBinding.stepDetailsFragment.setSystemUiVisibility(
                            View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
                }
            }
        }else if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT){
            getWindow().clearFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN);
            updateButtons();
            getSupportActionBar().show();
            mBinding.stepDetailsFragment.setSystemUiVisibility(View.SYSTEM_UI_FLAG_VISIBLE);
        }
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        fullScreen();
    }
}

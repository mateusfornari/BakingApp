package com.example.android.bakingapp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.adapters.RecipeStepListAdapter;
import com.example.android.bakingapp.databinding.FragmentRecipeStepsBinding;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.domain.RecipeStep;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateus on 16/06/17.
 */

public class RecipeStepsFragment extends Fragment {

    private FragmentRecipeStepsBinding mBinding;

    private Recipe mRecipe;

    private RecipeStepListAdapter mStepsAdapter;

    private static final String LOG_TAG = "RecipeStepsFragment";

    RecipeStepListAdapter.OnRecipeStepClickListener mListener;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try{
            mListener = (RecipeStepListAdapter.OnRecipeStepClickListener) context;
        }catch (ClassCastException e){
            e.printStackTrace();
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentRecipeStepsBinding.inflate(inflater, container, false);

        mRecipe = getActivity().getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);

        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext());
        mBinding.rvStepsList.setLayoutManager(layoutManager);

        mStepsAdapter = new RecipeStepListAdapter(mListener);
        mBinding.rvStepsList.setAdapter(mStepsAdapter);

        List<Object> steps = new ArrayList<>();
        steps.add(getString(R.string.item_recipe_ingredients));
        for(RecipeStep step : mRecipe.getSteps())
            steps.add(step);
        mStepsAdapter.swapSteps(steps);

        return mBinding.getRoot();
    }

}

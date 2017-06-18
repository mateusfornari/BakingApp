package com.example.android.bakingapp;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.adapters.IngredientListAdapter;
import com.example.android.bakingapp.databinding.FragmentIngredientsBinding;
import com.example.android.bakingapp.domain.Recipe;

/**
 * Created by mateus on 17/06/17.
 */

public class IngredientsFragment extends Fragment {

    private FragmentIngredientsBinding mBinding;
    private IngredientListAdapter mIngredientsAdapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mBinding = FragmentIngredientsBinding.inflate(inflater, container, false);

        Recipe recipe = getActivity().getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);

        LinearLayoutManager manager = new LinearLayoutManager(getContext());
        mBinding.rvIngredientsList.setLayoutManager(manager);

        mIngredientsAdapter = new IngredientListAdapter();
        mBinding.rvIngredientsList.setAdapter(mIngredientsAdapter);

        if(recipe != null){
            mIngredientsAdapter.swapIngredients(recipe.getIngredients());
        }

        return mBinding.getRoot();
    }
}

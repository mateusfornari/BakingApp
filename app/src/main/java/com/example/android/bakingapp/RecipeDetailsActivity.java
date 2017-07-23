package com.example.android.bakingapp;

import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.android.bakingapp.adapters.RecipeStepListAdapter;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.domain.RecipeStep;

public class RecipeDetailsActivity extends AppCompatActivity implements RecipeStepListAdapter.OnRecipeStepClickListener {

    private boolean isTablet = false;

    private Recipe mRecipe;

    public static final String EXTRA_STEP_POSITION = "extra_step_position";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);

        isTablet = findViewById(R.id.step_datails_content) != null;

        mRecipe = getIntent().getParcelableExtra(MainActivity.EXTRA_RECIPE);

        setTitle(mRecipe.getName());

        if(isTablet && savedInstanceState == null) {
            onIngredientsClick();
        }
    }

    @Override
    public void onRecipeStepClick(RecipeStep step) {
        if(!isTablet) {
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(EXTRA_STEP_POSITION, step.getId());
            intent.putExtra(MainActivity.EXTRA_RECIPE, mRecipe);
            startActivity(intent);
        }else{
            StepDetailsFragment fragment = new StepDetailsFragment();
            fragment.setStep(mRecipe.getSteps().get(step.getId()));
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.step_datails_content, fragment).commit();
        }
    }

    @Override
    public void onIngredientsClick() {
        if(!isTablet){
            Intent intent = new Intent(this, StepDetailsActivity.class);
            intent.putExtra(EXTRA_STEP_POSITION, -1);
            intent.putExtra(MainActivity.EXTRA_RECIPE, mRecipe);
            startActivity(intent);
        }else{
            IngredientsFragment fragment = new IngredientsFragment();
            FragmentManager manager = getSupportFragmentManager();
            manager.beginTransaction().replace(R.id.step_datails_content, fragment).commit();
        }
    }

    @Override
    public boolean isTablet() {
        return isTablet;
    }


}

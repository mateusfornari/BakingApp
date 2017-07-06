package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.android.bakingapp.adapters.RecipeListAdapter;
import com.example.android.bakingapp.databinding.ActivityMainBinding;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.utilities.JsonUtils;
import com.example.android.bakingapp.utilities.RecipeLoader;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements
        RecipeListAdapter.OnRecipeClickListener,
        RecipeLoader.OnLoadListener{

    private static final String LOG_TAG = "MainActivity";

    private static final String BUNDLE_RECIPES = "bundle_recipes";
    public static final String EXTRA_RECIPE = "extra_recipe";
    private ActivityMainBinding mBinding;

    private RecipeListAdapter mRecipeAdapter;

    private ArrayList<Recipe> mRecipes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        LinearLayoutManager layoutManager = new GridLayoutManager(this, getResources().getInteger(R.integer.grid_columns));
        mBinding.rvRecipeList.setLayoutManager(layoutManager);

        mRecipeAdapter = new RecipeListAdapter(this);
        mBinding.rvRecipeList.setAdapter(mRecipeAdapter);

        if(savedInstanceState != null && savedInstanceState.containsKey(BUNDLE_RECIPES)){
            Log.d(LOG_TAG, "There is saved instance state");
            mRecipes = savedInstanceState.getParcelableArrayList(BUNDLE_RECIPES);
            mRecipeAdapter.swapRecipes(mRecipes);
        }else {
            Log.d(LOG_TAG, "No saved instance state");
            loadRecipes();
        }

    }

    @Override
    public void onRecipeClick(Recipe recipe) {
        Log.d(LOG_TAG, "OnRecipeClick: " + recipe.getName());
        Intent intent = new Intent(this, RecipeDetailsActivity.class);
        intent.putExtra(EXTRA_RECIPE, recipe);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        if(mRecipes != null){
            outState.putParcelableArrayList(BUNDLE_RECIPES, mRecipes);
        }
        super.onSaveInstanceState(outState);
    }

    private void loadRecipes(){
        mBinding.loadingIndicator.setVisibility(View.VISIBLE);
        RecipeLoader loader = new RecipeLoader(this, this);
        loader.load();
    }


    @Override
    public void onLoaded(ArrayList<Recipe> data) {
        if(data != null){
            mBinding.emptyRecipesView.setVisibility(View.GONE);
            mBinding.rvRecipeList.setVisibility(View.VISIBLE);
            mRecipes = data;
            mRecipeAdapter.swapRecipes(mRecipes);
            mBinding.loadingIndicator.setVisibility(View.INVISIBLE);

            AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this);
            int[] appWidgetIds = appWidgetManager.getAppWidgetIds(new ComponentName(this, BakingAppWidget.class));
            //Trigger data update to handle the GridView widgets and force a data refresh
            appWidgetManager.notifyAppWidgetViewDataChanged(appWidgetIds, R.id.widget_grid_view);
            for(int id : appWidgetIds){
                BakingAppWidget.updateAppWidget(this, appWidgetManager, id);
            }

        }
    }

    @Override
    public void onError(String error) {
        mBinding.loadingIndicator.setVisibility(View.INVISIBLE);
        mBinding.emptyRecipesView.setVisibility(View.VISIBLE);
        mBinding.rvRecipeList.setVisibility(View.GONE);
        Toast.makeText(getApplicationContext(), getString(R.string.msg_error_internet), Toast.LENGTH_LONG).show();
        Log.d(LOG_TAG, "Erro: " + error);
    }
}
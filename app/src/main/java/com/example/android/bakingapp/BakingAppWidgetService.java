package com.example.android.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.domain.Ingredient;
import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.utilities.RecipeLoader;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by mateus on 01/07/17.
 */

public class BakingAppWidgetService extends RemoteViewsService {
    private static final String LOG_TAG = "BakingAppWidgetService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(LOG_TAG, "OnGetViewFactory");
        int id = intent.getIntExtra(BakingAppWidget.EXTRA_WIDGET_ID, 0);
        return new GridWidgetFactory(this.getApplicationContext(), id);
    }
}

class GridWidgetFactory implements RemoteViewsService.RemoteViewsFactory{

    private static final String LOG_TAG = "GridWidgetFactory";
    private Context context;
    private ArrayList<Recipe> recipes;
    private List<Ingredient> ingredients;
    private Recipe recipe;
    private int widgetId;
    GridWidgetFactory(Context context, int widgetId){
        this.context = context;
        this.widgetId = widgetId;

    }

    private int getDesiredRecipeId(){
        SharedPreferences preferences = context.getSharedPreferences(MainActivity.SHARED_PREFERENCES_NAME, Context.MODE_PRIVATE);
        return preferences.getInt(context.getString(R.string.pref_desired_recipe), 1);
    }
    @Override
    public void onCreate() {
        Log.d(LOG_TAG, "Entrou onCreate");

    }

    @Override
    public void onDataSetChanged() {
        Log.d(LOG_TAG, "onDataSetChanged");
        RecipeLoader loader = new RecipeLoader(context, null);
        this.recipes = loader.loadSync();
        if(this.recipes != null) {
            int id = getDesiredRecipeId();
            for(Recipe r : this.recipes) {
                Log.d(LOG_TAG, "ID: " + id + "/" + r.getId());
                if(r.getId() == id) {
                    this.recipe = r;
                    this.ingredients = r.getIngredients();
                    break;
                }
            }
        }
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount");
        if(this.ingredients != null){
            Log.d(LOG_TAG, "Count: " + this.ingredients.size());
            return this.ingredients.size();
        }
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Log.d(LOG_TAG, "Position: " + position);
        Ingredient ingredient = this.ingredients.get(position);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        if(position == 0){
            views.setViewVisibility(R.id.tv_recipe_name, View.VISIBLE);
            views.setTextViewText(R.id.tv_recipe_name, recipe.getName());
        }else{
            views.setViewVisibility(R.id.tv_recipe_name, View.GONE);
        }
        views.setTextViewText(R.id.tv_ingredient_quantity, String.valueOf(ingredient.getQuantity()));
        views.setTextViewText(R.id.tv_ingredient_measure, ingredient.getMeasure());
        views.setTextViewText(R.id.tv_ingredient_description, ingredient.getDescription());

        return views;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }

}
package com.example.android.bakingapp;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.android.bakingapp.domain.Recipe;
import com.example.android.bakingapp.utilities.RecipeLoader;

import java.util.ArrayList;

/**
 * Created by mateus on 01/07/17.
 */

public class BakingAppWidgetService extends RemoteViewsService {
    private static final String LOG_TAG = "BakingAppWidgetService";

    @Override
    public RemoteViewsFactory onGetViewFactory(Intent intent) {
        Log.d(LOG_TAG, "OnGetViewFactory");
        return new GridWidgetFactory(this.getApplicationContext());
    }
}

class GridWidgetFactory implements RemoteViewsService.RemoteViewsFactory{

    private static final String LOG_TAG = "GridWidgetFactory";
    private Context context;
    private ArrayList<Recipe> recipes;

    public GridWidgetFactory(Context context){
        this.context = context;

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
    }

    @Override
    public void onDestroy() {
        Log.d(LOG_TAG, "onDestroy");
    }

    @Override
    public int getCount() {
        Log.d(LOG_TAG, "getCount");
        if(this.recipes != null) return this.recipes.size();
        return 0;
    }

    @Override
    public RemoteViews getViewAt(int position) {
        Recipe recipe = recipes.get(position);
        String name = recipe.getName();
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.baking_app_widget);
        views.setTextViewText(R.id.appwidget_text, name);

        Bundle extras = new Bundle();
        extras.putParcelable(MainActivity.EXTRA_RECIPE, recipe);
        Intent fillIntent = new Intent();
        fillIntent.putExtras(extras);

        views.setOnClickFillInIntent(R.id.appwidget_text, fillIntent);
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
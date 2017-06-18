package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.domain.Recipe;

import java.util.List;

/**
 * Created by mateus on 15/06/17.
 */

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;

    private Context mContext;

    private OnRecipeClickListener onRecipeClickListener;

    public RecipeListAdapter(OnRecipeClickListener listener){
        this.onRecipeClickListener = listener;
    }

    @Override
    public RecipeViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        View view = inflater.inflate(R.layout.recipe_list_item, parent, false);
        return new RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipeViewHolder holder, int position) {
        Recipe recipe = recipes.get(position);
        holder.bind(recipe);
    }

    @Override
    public int getItemCount() {
        if(this.recipes != null) return this.recipes.size();
        return 0;
    }

    public void swapRecipes(List<Recipe> recipes){
        this.recipes = recipes;
        notifyDataSetChanged();
    }



    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private TextView mRecipeNameTextView;

        private Recipe mRecipe;


        public RecipeViewHolder(View itemView) {
            super(itemView);
            mRecipeNameTextView = (TextView)itemView.findViewById(R.id.tv_recipe_name);
            itemView.setOnClickListener(this);
        }

        public void bind(Recipe recipe){
            mRecipe = recipe;
            mRecipeNameTextView.setText(recipe.getName());
        }

        @Override
        public void onClick(View v) {
            if(onRecipeClickListener != null){
                onRecipeClickListener.onRecipeClick(mRecipe);
            }
        }
    }

    public interface OnRecipeClickListener{
        void onRecipeClick(Recipe recipe);
    }
}

package com.example.android.bakingapp.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.R;
import com.example.android.bakingapp.databinding.RecipeListItemBinding;
import com.example.android.bakingapp.domain.Recipe;
import com.squareup.picasso.Picasso;

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
        RecipeListItemBinding mBinding = RecipeListItemBinding.inflate(inflater);
        RecipeViewHolder viewHolder = new RecipeViewHolder(mBinding.getRoot());
        viewHolder.setmBinding(mBinding);
        return viewHolder;
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

        private Recipe mRecipe;

        private RecipeListItemBinding mBinding;

        public RecipeViewHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(this);
        }

        public void setmBinding(RecipeListItemBinding mBinding) {
            this.mBinding = mBinding;
        }

        public void bind(Recipe recipe){
            mRecipe = recipe;
            mBinding.tvRecipeName.setText(recipe.getName());
            mBinding.tvRecipeServings.setText(
                    mContext.getString(R.string.a11y_servings, String.valueOf(recipe.getServings())));
            if(recipe.getImageUrl().isEmpty()){
                mBinding.ivRecipeImage.setVisibility(View.GONE);
            }else{
                Picasso.with(mContext).load(recipe.getImageUrl()).into(mBinding.ivRecipeImage);
            }
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

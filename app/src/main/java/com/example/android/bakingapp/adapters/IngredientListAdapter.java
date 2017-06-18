package com.example.android.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.databinding.IngredientListItemBinding;
import com.example.android.bakingapp.domain.Ingredient;

import java.util.List;

/**
 * Created by mateus on 17/06/17.
 */

public class IngredientListAdapter extends RecyclerView.Adapter<IngredientListAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients;

    @Override
    public IngredientViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        IngredientListItemBinding mBinding = IngredientListItemBinding.inflate(inflater, parent, false);
        IngredientViewHolder ingredientViewHolder = new IngredientViewHolder(mBinding.getRoot());
        ingredientViewHolder.setmBinding(mBinding);
        return ingredientViewHolder;
    }

    @Override
    public void onBindViewHolder(IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient);
    }

    @Override
    public int getItemCount() {
        if(ingredients != null) return ingredients.size();
        return 0;
    }

    public void swapIngredients(List<Ingredient> ingredients){
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public class IngredientViewHolder extends RecyclerView.ViewHolder {

        private IngredientListItemBinding mBinding;

        public IngredientViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(Ingredient ingredient){
            mBinding.tvIngredientQuantity.setText(String.valueOf(ingredient.getQuantity()));
            mBinding.tvIngredientMeasure.setText(ingredient.getMeasure());
            mBinding.tvIngredientDescription.setText(ingredient.getDescription());
        }

        public void setmBinding(IngredientListItemBinding mBinding) {
            this.mBinding = mBinding;
        }
    }
}

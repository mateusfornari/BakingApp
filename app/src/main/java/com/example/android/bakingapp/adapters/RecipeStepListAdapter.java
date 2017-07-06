package com.example.android.bakingapp.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.android.bakingapp.databinding.RecipeStepListItemBinding;
import com.example.android.bakingapp.domain.RecipeStep;

import java.util.List;

/**
 * Created by mateus on 15/06/17.
 */

public class RecipeStepListAdapter extends RecyclerView.Adapter<RecipeStepListAdapter.StepViewHolder> {

    private List<Object> mSteps;

    private OnRecipeStepClickListener recipeStepClickListener;

    public RecipeStepListAdapter(OnRecipeStepClickListener listener){
        recipeStepClickListener = listener;
    }

    @Override
    public StepViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecipeStepListItemBinding mBinding = RecipeStepListItemBinding.inflate(inflater, parent, false);
        StepViewHolder viewHolder = new StepViewHolder(mBinding.getRoot());
        viewHolder.setmBinding(mBinding);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(StepViewHolder holder, int position) {
        Object step = mSteps.get(position);
        if(step instanceof RecipeStep) {
            holder.bind(((RecipeStep)step).getShortDescription());
        }else if(step instanceof String){
            holder.bind((String)step);
        }
    }

    @Override
    public int getItemCount() {
        if(mSteps != null) return mSteps.size();
        return 0;
    }

    public void swapSteps(List<Object> steps){
        mSteps = steps;
        notifyDataSetChanged();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private RecipeStepListItemBinding mBinding;

        public StepViewHolder(View itemView) {
            super(itemView);
        }

        public void bind(String str){
            mBinding.tvStepDescription.setText(str);
            mBinding.getRoot().setOnClickListener(this);
        }

        public void setmBinding(RecipeStepListItemBinding mBinding) {
            this.mBinding = mBinding;
        }

        @Override
        public void onClick(View v) {
            if(recipeStepClickListener != null){
                Object step = mSteps.get(getAdapterPosition());
                if(step instanceof RecipeStep) {
                    recipeStepClickListener.onRecipeStepClick((RecipeStep) step);
                }else{
                    recipeStepClickListener.onIngredientsClick();
                }
            }
        }
    }

    public interface OnRecipeStepClickListener{
        void onRecipeStepClick(RecipeStep step);
        void onIngredientsClick();
    }
}

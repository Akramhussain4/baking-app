package com.hussain.akram.bakingapp.adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hussain.akram.bakingapp.R;
import com.hussain.akram.bakingapp.databinding.LayoutRecipeItemBinding;
import com.hussain.akram.bakingapp.model.Recipe;
import com.hussain.akram.bakingapp.util.GlideApp;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.RecipeViewHolder> {

    private final List<Recipe> mRecipes;
    private final RecipeClickListener mRecipeClickListener;
    private final Context mContext;

    public RecipeAdapter(List<Recipe> recipes, Context context, RecipeClickListener recipeClickListener) {
        this.mRecipes = recipes;
        this.mContext = context;
        this.mRecipeClickListener = recipeClickListener;
        notifyDataSetChanged();
    }

    public interface RecipeClickListener {
        void recipeIndex(int index);
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_recipe_item, viewGroup, false);
        return new RecipeAdapter.RecipeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder recipeViewHolder, int i) {
        recipeViewHolder.binding.setVariable(com.hussain.akram.bakingapp.BR.recipe, mRecipes.get(i));
        recipeViewHolder.binding.executePendingBindings();
        GlideApp.with(mContext)
                .load(mRecipes.get(i).getImage())
                .placeholder(R.drawable.recipe_placeholder)
                .into(recipeViewHolder.recipeThumb);
    }

    @Override
    public int getItemCount() {
        return mRecipes.size();
    }

    class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final LayoutRecipeItemBinding binding;
        @BindView(R.id.recipeName)
        TextView recipeName;
        @BindView(R.id.recipeThumbnail)
        ImageView recipeThumb;

        private RecipeViewHolder(final View itemView) {
            super(itemView.getRootView());
            binding = DataBindingUtil.bind(itemView);
            ButterKnife.bind(this, itemView);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            mRecipeClickListener.recipeIndex(getAdapterPosition());
        }
    }
}

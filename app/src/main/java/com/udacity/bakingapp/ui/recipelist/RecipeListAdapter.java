package com.udacity.bakingapp.ui.recipelist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.model.Recipe;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListViewHolder> {

    private List<Recipe> recipes;
    private RecipeListViewModel viewModel;

    public RecipeListAdapter(List<Recipe> recipes, RecipeListViewModel viewModel) {
        this.recipes = recipes;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public RecipeListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_item_list, parent, false);
        return new RecipeListViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeListViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }
}

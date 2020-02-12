package com.udacity.bakingapp.ui.recipe;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.model.Step;

import java.util.List;

public class StepAdapter extends RecyclerView.Adapter<StepViewHolder> {

    private RecipeViewModel viewModel;
    private List<Step> steps;
    private String ingredients;

    public StepAdapter(RecipeViewModel viewModel, List<Step> steps, String ingredients) {
        this.viewModel = viewModel;
        this.steps = steps;
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recipe_step_item_list, parent, false);
        return new StepViewHolder(view, viewModel);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        if (position == 0)
            holder.bind(ingredients);
        else
            holder.bind(steps.get(position - 1));
    }

    @Override
    public int getItemCount() {
        return steps.size() + 1;
    }
}

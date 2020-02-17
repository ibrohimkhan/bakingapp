package com.udacity.bakingapp.ui.recipelist;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.common.Event;
import com.udacity.bakingapp.data.model.Recipe;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RecipeListViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_recipe_name)
    TextView textView;

    private RecipeListViewModel viewModel;
    private Recipe recipe;

    public RecipeListViewHolder(@NonNull View itemView, RecipeListViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Recipe recipe) {
        this.recipe = recipe;

        if (textView != null) {
            textView.setText(recipe.name);
        }
    }

    @Override
    public void onClick(View v) {
        viewModel.onItemSelected(new Event<>(recipe));
    }
}

package com.udacity.bakingapp.ui.recipe;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.common.Event;
import com.udacity.bakingapp.data.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.tv_short_step_description)
    TextView textView;

    private RecipeViewModel viewModel;
    private Step step;
    private String ingredients;

    public StepViewHolder(@NonNull View itemView, RecipeViewModel viewModel) {
        super(itemView);
        this.viewModel = viewModel;

        ButterKnife.bind(this, itemView);
        itemView.setOnClickListener(this);
    }

    public void bind(Step step) {
        this.step = step;

        if (textView != null) {
            textView.setText(step.shortDescription);
        }
    }

    public void bind(String ingredients) {
        this.ingredients = ingredients;

        if (textView != null) {
            textView.setText(ingredients);
        }
    }

    @Override
    public void onClick(View v) {
        if (((TextView) v).getText().equals(ingredients))
            viewModel.onIngredientSelected(new Event<>(true));
        else
            viewModel.onItemSelected(new Event<>(step));
    }
}

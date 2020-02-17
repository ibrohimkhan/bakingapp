package com.udacity.bakingapp.ui.recipe;

import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.RecyclerView;

import com.squareup.picasso.Picasso;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.common.Event;
import com.udacity.bakingapp.data.model.Step;

import butterknife.BindView;
import butterknife.ButterKnife;

public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    @BindView(R.id.iv_thumbnail)
    @Nullable
    ImageView imageView;

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

        if (imageView != null && !TextUtils.isEmpty(step.thumbnailURL) && !step.thumbnailURL.endsWith(".mp4")) {
            Picasso.get()
                    .load(step.thumbnailURL)
                    .fit()
                    .into(imageView);

        } else if (imageView != null) {
            imageView.setVisibility(View.GONE);
        }
    }

    public void bind(String ingredients) {
        this.ingredients = ingredients;

        if (textView != null) {
            textView.setText(ingredients);
        }

        if (imageView != null) imageView.setVisibility(View.GONE);
    }

    @Override
    public void onClick(View v) {
        if (ingredients != null)
            viewModel.onIngredientSelected(new Event<>(true));
        else
            viewModel.onItemSelected(new Event<>(step));
    }
}

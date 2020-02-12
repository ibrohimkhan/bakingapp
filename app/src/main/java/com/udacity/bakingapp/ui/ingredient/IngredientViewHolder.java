package com.udacity.bakingapp.ui.ingredient;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.model.Ingredient;

import butterknife.BindView;
import butterknife.ButterKnife;

public class IngredientViewHolder extends RecyclerView.ViewHolder {

    @BindView(R.id.tv_ingredient)
    TextView tvIngredient;

    @BindView(R.id.tv_measure)
    TextView tvMeasure;

    @BindView(R.id.tv_quantity)
    TextView tvQuantity;

    public IngredientViewHolder(@NonNull View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }

    public void bind(Ingredient ingredient) {
        if (tvIngredient != null) tvIngredient.setText(ingredient.ingredient);
        if (tvMeasure != null) tvMeasure.setText(ingredient.measure);
        if (tvQuantity != null) tvQuantity.setText(String.valueOf(ingredient.quantity));
    }
}

package com.udacity.bakingapp.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Ingredient {
    @Expose
    @SerializedName("quantity")
    public final double quantity;

    @Expose
    @SerializedName("measure")
    public final String measure;

    @Expose
    @SerializedName("ingredient")
    public final String ingredient;

    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}

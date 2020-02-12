package com.udacity.bakingapp.data.model;

public class Ingredient {
    public final double quantity;
    public final String measure;
    public final String ingredient;

    public Ingredient(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }
}

package com.udacity.bakingapp.data.remote.response;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.udacity.bakingapp.data.model.Ingredient;
import com.udacity.bakingapp.data.model.Step;

import java.util.List;

public class RecipeResponse {

    @Expose
    @SerializedName("id")
    public final int id;

    @Expose
    @SerializedName("name")
    public final String name;

    @Expose
    @SerializedName("ingredients")
    public final List<Ingredient> ingredients;

    @Expose
    @SerializedName("steps")
    public final List<Step> steps;

    @Expose
    @SerializedName("servings")
    public final int servings;

    @Expose
    @SerializedName("image")
    public final String image;

    public RecipeResponse(int id, String name, List<Ingredient> ingredients, List<Step> steps, int servings, String image) {
        this.id = id;
        this.name = name;
        this.ingredients = ingredients;
        this.steps = steps;
        this.servings = servings;
        this.image = image;
    }
}

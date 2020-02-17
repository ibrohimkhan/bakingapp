package com.udacity.bakingapp.common;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.bakingapp.data.model.Ingredient;
import com.udacity.bakingapp.data.model.Recipe;
import com.udacity.bakingapp.data.model.Step;

import java.util.List;

public class SharedViewModel extends ViewModel {

    private final MutableLiveData<Event<Recipe>> recipes = new MutableLiveData<>();
    private final MutableLiveData<Event<Step>> step = new MutableLiveData<>();
    private final MutableLiveData<Event<List<Ingredient>>> ingredients = new MutableLiveData<>();

    public void selectedRecipe(Event<Recipe> recipe) {
        recipes.setValue(recipe);
    }

    public MutableLiveData<Event<Recipe>> getSelectedRecipe() {
        return recipes;
    }

    public void selectedIngredients(Event<List<Ingredient>> ingredients) {
        this.ingredients.setValue(ingredients);
    }

    public MutableLiveData<Event<List<Ingredient>>> getSelectedIngredients() {
        return ingredients;
    }

    public void selectedStep(Event<Step> step) {
        this.step.setValue(step);
    }

    public MutableLiveData<Event<Step>> getStep() {
        return step;
    }
}

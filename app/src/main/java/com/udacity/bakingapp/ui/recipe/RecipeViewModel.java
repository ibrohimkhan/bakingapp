package com.udacity.bakingapp.ui.recipe;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.bakingapp.common.Event;
import com.udacity.bakingapp.data.model.Step;

public class RecipeViewModel extends ViewModel {

    public final MutableLiveData<Event<Step>> step = new MutableLiveData<>();
    public final MutableLiveData<Event<Boolean>> ingredient = new MutableLiveData<>();

    public void onItemSelected(Event<Step> step) {
        this.step.setValue(step);
    }

    public void onIngredientSelected(Event<Boolean> event) {
        ingredient.setValue(event);
    }
}

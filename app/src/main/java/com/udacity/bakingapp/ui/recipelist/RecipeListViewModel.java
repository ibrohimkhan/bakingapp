package com.udacity.bakingapp.ui.recipelist;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.udacity.bakingapp.common.Event;
import com.udacity.bakingapp.data.local.IngredientEntity;
import com.udacity.bakingapp.data.model.Recipe;
import com.udacity.bakingapp.data.repository.RecipeRepository;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;

public class RecipeListViewModel extends ViewModel {

    private static final String TAG = RecipeListViewModel.class.getSimpleName();

    private CompositeDisposable disposable = new CompositeDisposable();

    MutableLiveData<List<Recipe>> recipes = new MutableLiveData<>();
    MutableLiveData<Boolean> withError = new MutableLiveData<>();
    MutableLiveData<Event<Recipe>> recipe = new MutableLiveData<>();
    MutableLiveData<Event<Boolean>> loading = new MutableLiveData<>();

    @Override
    protected void onCleared() {
        disposable.dispose();
        super.onCleared();
    }

    public RecipeListViewModel() {
        loadRecipes();
    }

    public void onItemSelected(Event<Recipe> recipe) {
        this.recipe.setValue(recipe);
    }

    private void loadRecipes() {
        loading.setValue(new Event<>(true));

        disposable.add(
                RecipeRepository.loadRecipes()
                        .subscribeOn(Schedulers.io())
                        .subscribe(this::notifyUI, this::handleError)
        );
    }

    private void handleError(Throwable throwable) {
        Log.e(TAG, throwable.getMessage());
        loading.postValue(new Event<>(false));
        withError.postValue(true);
    }

    private void notifyUI(List<Recipe> recipes) {
        loading.postValue(new Event<>(false));
        this.recipes.postValue(recipes);

        List<IngredientEntity> items = RecipeRepository.getFavoriteIngredients()
                .subscribeOn(Schedulers.io())
                .blockingGet();

        if (items == null || items.isEmpty())
            RecipeRepository.saveDefaultIngredients(recipes.get(1).ingredients);
    }
}

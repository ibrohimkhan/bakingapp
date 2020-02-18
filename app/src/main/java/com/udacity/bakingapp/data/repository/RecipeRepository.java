package com.udacity.bakingapp.data.repository;

import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.content.Context;

import com.udacity.bakingapp.BakingAppWidgetProvider;
import com.udacity.bakingapp.BakingApplication;
import com.udacity.bakingapp.R;
import com.udacity.bakingapp.data.local.AppDatabase;
import com.udacity.bakingapp.data.local.IngredientEntity;
import com.udacity.bakingapp.data.model.Ingredient;
import com.udacity.bakingapp.data.model.Recipe;
import com.udacity.bakingapp.data.remote.NetworkService;
import com.udacity.bakingapp.data.remote.Networking;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Completable;
import io.reactivex.CompletableSource;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;

public final class RecipeRepository {

    private static NetworkService service = Networking.createService(NetworkService.class);
    private static final AppDatabase database = AppDatabase.getInstance();

    private RecipeRepository() {
    }

    public static Single<List<Recipe>> loadRecipes() {
        return service.loadRecipes()
                .toObservable()
                .flatMapIterable(list -> list)
                .map(item -> new Recipe(
                        item.id,
                        item.name,
                        item.ingredients,
                        item.steps,
                        item.servings,
                        item.image
                ))
                .toList();
    }

    public static Single<List<IngredientEntity>> getFavoriteIngredients() {
        return database.IngredientDao().select();
    }

    public static void saveDefaultIngredients(List<Ingredient> ingredients) {
        saveIngredients(ingredients);
    }

    public static void saveAsFavoriteIngredients(List<Ingredient> ingredients) {
        saveIngredients(ingredients);
    }

    private static void saveIngredients(List<Ingredient> ingredients) {
        List<IngredientEntity> entities = new ArrayList<>();

        for (Ingredient ingredient : ingredients) {
            IngredientEntity entity = new IngredientEntity(
                    ingredient.quantity,
                    ingredient.measure,
                    ingredient.ingredient
            );

            entities.add(entity);
        }

        Completable.fromAction(RecipeRepository::delete)
                .subscribeOn(Schedulers.io())
                .andThen((CompletableSource) s -> {
                    database.IngredientDao().insert(entities).subscribeOn(Schedulers.io()).subscribe();
                }).subscribe();

        updateWidgetUI();
    }

    private static void delete() {
        database.IngredientDao().delete();
    }

    private static void updateWidgetUI() {
        Context context = BakingApplication.getContext();
        AppWidgetManager manager = AppWidgetManager.getInstance(context);
        int[] ids = manager.getAppWidgetIds(new ComponentName(context, BakingAppWidgetProvider.class));

        manager.notifyAppWidgetViewDataChanged(ids, R.id.sv_ingredients);
        BakingAppWidgetProvider.updateWidgetUI(context, manager, ids);
    }
}

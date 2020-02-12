package com.udacity.bakingapp.data.repository;

import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.udacity.bakingapp.BakingApplication;
import com.udacity.bakingapp.data.model.Recipe;

import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.util.Collections;
import java.util.List;

import io.reactivex.Single;

public final class RecipeRepository {

    private static List<Recipe> recipes;

    private RecipeRepository() {}

    public static Single<List<Recipe>> loadRecipes() {
        if (recipes != null && !recipes.isEmpty()) return Single.just(recipes);

        try (Reader reader = new InputStreamReader(BakingApplication.getContext().getAssets().open("baking.json"), "UTF-8")) {
            recipes = new Gson().fromJson(reader, new TypeToken<List<Recipe>>() {}.getType());

            if (recipes != null && !recipes.isEmpty())
                return Single.just(recipes);

        } catch (IOException ex) {
            Log.e("RecipeRepository:", ex.getMessage());
        }

        return Single.just(Collections.emptyList());
    }
}

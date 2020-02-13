package com.udacity.bakingapp.data.repository;

import com.udacity.bakingapp.data.model.Recipe;
import com.udacity.bakingapp.data.remote.NetworkService;
import com.udacity.bakingapp.data.remote.Networking;

import java.util.List;

import io.reactivex.Single;

public final class RecipeRepository {

    private static NetworkService service = Networking.createService(NetworkService.class);

    private RecipeRepository() {}

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
}

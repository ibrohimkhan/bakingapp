package com.udacity.bakingapp.data.remote;

import com.udacity.bakingapp.BuildConfig;
import com.udacity.bakingapp.data.remote.response.RecipeResponse;

import java.util.List;

import io.reactivex.Single;
import retrofit2.http.GET;

public interface NetworkService {

    @GET(BuildConfig.BASE_URL + Endpoints.JSON_FILE)
    Single<List<RecipeResponse>> loadRecipes();
}
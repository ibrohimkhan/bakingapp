package com.udacity.bakingapp.data.local;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.util.Collections;
import java.util.List;

public class IngredientTypeConverter {

    private IngredientTypeConverter() {}

    @TypeConverter
    public static String fromListOfTrailers(List<IngredientEntity> values) {
        if (values == null || values.isEmpty()) return null;
        String json = new Gson().toJson(values);

        return json;
    }

    @TypeConverter
    public static List<IngredientEntity> toListOfTrailers(String value) {
        if (value == null) return Collections.emptyList();

        return new Gson().fromJson(value, new TypeToken<List<IngredientEntity>>(){}.getType());
    }
}

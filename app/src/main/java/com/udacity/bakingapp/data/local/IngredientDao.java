package com.udacity.bakingapp.data.local;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import io.reactivex.Completable;
import io.reactivex.Single;

@Dao
public interface IngredientDao {
    @Query("select * from ingredients")
    Single<List<IngredientEntity>> select();

    @Insert
    Completable insert(List<IngredientEntity> ingredients);

    @Query("delete from ingredients")
    void delete();
}

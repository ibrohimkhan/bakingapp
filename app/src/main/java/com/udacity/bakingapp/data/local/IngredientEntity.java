package com.udacity.bakingapp.data.local;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

@Entity(tableName = "ingredients")
public class IngredientEntity {

    @PrimaryKey(autoGenerate = true)
    private int id;

    @ColumnInfo(name = "quantity")
    public final double quantity;

    @ColumnInfo(name = "measure")
    public final String measure;

    @ColumnInfo(name = "ingredient")
    public final String ingredient;

    public IngredientEntity(int id, double quantity, String measure, String ingredient) {
        this.id = id;
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    @Ignore
    public IngredientEntity(double quantity, String measure, String ingredient) {
        this.quantity = quantity;
        this.measure = measure;
        this.ingredient = ingredient;
    }

    public int getId() {
        return id;
    }
}

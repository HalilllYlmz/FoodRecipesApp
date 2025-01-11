package com.example.modernfoodrecipesapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.example.modernfoodrecipesapp.data.database.RecipesTypeConverter
import com.example.modernfoodrecipesapp.model.FoodRecipe
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    @TypeConverters(RecipesTypeConverter::class)
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
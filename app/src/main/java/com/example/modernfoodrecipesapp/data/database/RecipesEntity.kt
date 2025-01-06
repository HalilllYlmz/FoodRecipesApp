package com.example.modernfoodrecipesapp.data.database

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.modernfoodrecipesapp.model.FoodRecipe
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.RECIPES_TABLE

@Entity(tableName = RECIPES_TABLE)
class RecipesEntity(
    var foodRecipe: FoodRecipe
) {
    @PrimaryKey(autoGenerate = true)
    var id: Int = 0
}
package com.example.modernfoodrecipesapp.data.database.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.modernfoodrecipesapp.model.Result
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.FAVORITES_RECIPES_TABLE

@Entity(tableName = FAVORITES_RECIPES_TABLE)
data class FavoritesEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    var result: Result
)
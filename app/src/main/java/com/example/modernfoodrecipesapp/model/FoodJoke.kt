package com.example.modernfoodrecipesapp.model

import com.google.gson.annotations.SerializedName
import androidx.room.ColumnInfo

data class FoodJoke(
    @SerializedName("text")
    @ColumnInfo(name = "text")
    val text: String
)
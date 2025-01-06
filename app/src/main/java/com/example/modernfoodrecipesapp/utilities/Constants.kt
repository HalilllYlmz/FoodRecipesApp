package com.example.modernfoodrecipesapp.utilities

class Constants {

    companion object {
        const val API_KEY = "3d2cb5f817894db0b4a61f4a5bf2c2ad"
        const val BASE_URL = "https://api.spoonacular.com"


        // API Query Keys
        const val QUERY_NUMBER = "number"
        const val QUERY_API_KEY = "apiKey"
        const val QUERY_TYPE = "type"
        const val QUERY_DIET = "diet"
        const val QUERY_ADD_RECIPE_INFORMATION = "addRecipeInformation"
        const val QUERY_FILL_INGREDIENTS = "fillIngredients"


        // Room Database
        const val DATABASE_NAME = "recipes_database"
        const val RECIPES_TABLE = "recipes_table"
    }

}
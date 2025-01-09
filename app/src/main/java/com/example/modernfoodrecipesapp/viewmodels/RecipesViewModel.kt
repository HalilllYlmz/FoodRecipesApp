package com.example.modernfoodrecipesapp.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.example.modernfoodrecipesapp.data.DataStoreRepository
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.API_KEY
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.DEFAULT_RECIPES_NUMBER
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.QUERY_ADD_RECIPE_INFORMATION
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.QUERY_API_KEY
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.QUERY_DIET
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.QUERY_FILL_INGREDIENTS
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.QUERY_NUMBER
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.QUERY_TYPE
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RecipesViewModel @Inject constructor(
    private val dataStoreRepository: DataStoreRepository
) : ViewModel() {

    private var mealType = DEFAULT_MEAL_TYPE
    private var dietType = DEFAULT_DIET_TYPE

    var networkStatus = false
    var backOnline = false

    val readMealAndDietType = dataStoreRepository.readMealAndDietType
    val readBackOnline = dataStoreRepository.readBackOnline.asLiveData()

    private val _networkStatusMessage = MutableLiveData<String>()
    val networkStatusMessage: LiveData<String> get() = _networkStatusMessage

    fun checkNetworkStatus() {
        if (!networkStatus) {
            _networkStatusMessage.value = "No network connection"
            saveBackOnline(true)
        } else if(networkStatus) {
            if(backOnline) {
                _networkStatusMessage.value = "We're back online"
                saveBackOnline(false)
            }
        }
    }

    fun saveBackOnline(backOnline: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveBackOnline(backOnline)
        }
    }


    fun saveMealAndDietType(mealType: String, mealTypeID: Int, dietType: String, dietTypeID: Int) =
        viewModelScope.launch(Dispatchers.IO) {
            dataStoreRepository.saveMealAndDietType(mealType, mealTypeID, dietType, dietTypeID)
        }

    fun applyQueries(): HashMap<String, String> {
        val queries: HashMap<String, String> = HashMap()

        viewModelScope.launch {
            readMealAndDietType.collect { value ->
                mealType = value.selectedMealType
                dietType = value.selectedDietType
            }
            println("MealType: $mealType")
            println("DietType: $dietType")
        }

        queries[QUERY_NUMBER] = DEFAULT_RECIPES_NUMBER
        queries[QUERY_API_KEY] = API_KEY
        queries[QUERY_TYPE] = mealType
        queries[QUERY_DIET] = dietType
        queries[QUERY_ADD_RECIPE_INFORMATION] = "true"
        queries[QUERY_FILL_INGREDIENTS] = "true"

        return queries
    }
}
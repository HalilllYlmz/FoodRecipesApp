package com.example.modernfoodrecipesapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.core.IOException
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.DEFAULT_DIET_TYPE
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.DEFAULT_MEAL_TYPE
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.PREFERENCES_BACK_ONLINE
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.PREFERENCES_DIET_TYPE
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.PREFERENCES_DIET_TYPE_ID
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.PREFERENCES_MEAL_TYPE
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.PREFERENCES_MEAL_TYPE_ID
import com.example.modernfoodrecipesapp.utilities.Constants.Companion.PREFERENCES_NAME
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import javax.inject.Inject


@ActivityRetainedScoped
class DataStoreRepository @Inject constructor(@ApplicationContext private val context: Context) {

    private object PreferenceKeys {
        val selectedMealType = stringPreferencesKey(PREFERENCES_MEAL_TYPE)
        val selectedMealTypeID = intPreferencesKey(PREFERENCES_MEAL_TYPE_ID)
        val selectedDietType = stringPreferencesKey(PREFERENCES_DIET_TYPE)
        val selectedDietTypeID = intPreferencesKey(PREFERENCES_DIET_TYPE_ID)
        val backOnline = booleanPreferencesKey(PREFERENCES_BACK_ONLINE)
    }

    private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = PREFERENCES_NAME)

    private val dataStore: DataStore<Preferences>
        get() = context.dataStore

    suspend fun saveMealAndDietType(
        mealType: String, mealTypeID: Int,
        dietType: String, dietTypeID: Int
    ) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.selectedMealType] = mealType
            preferences[PreferenceKeys.selectedMealTypeID] = mealTypeID
            preferences[PreferenceKeys.selectedDietType] = dietType
            preferences[PreferenceKeys.selectedDietTypeID] = dietTypeID
        }
    }

    suspend fun saveBackOnline(backOnline: Boolean) {
        dataStore.edit { preferences ->
            preferences[PreferenceKeys.backOnline] = backOnline
        }
    }


    val readMealAndDietType: Flow<MealAndDietType> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val selectedMealType = preferences[PreferenceKeys.selectedMealType] ?: DEFAULT_MEAL_TYPE
            val selectedMealTypeID = preferences[PreferenceKeys.selectedMealTypeID] ?: 0
            val selectedDietType = preferences[PreferenceKeys.selectedDietType] ?: DEFAULT_DIET_TYPE
            val selectedDietTypeID = preferences[PreferenceKeys.selectedDietTypeID] ?: 0
            MealAndDietType(
                selectedMealType,
                selectedMealTypeID,
                selectedDietType,
                selectedDietTypeID
            )
        }

    val readBackOnline: Flow<Boolean> = dataStore.data
        .catch { exception ->
            if (exception is IOException) {
                emit(emptyPreferences())
            } else {
                throw exception
            }
        }
        .map { preferences ->
            val backOnline = preferences[PreferenceKeys.backOnline] ?: false
            backOnline
        }

}


data class MealAndDietType(
    val selectedMealType: String,
    val selectedMealTypeID: Int,
    val selectedDietType: String,
    val selectedDietTypeID: Int,

    )
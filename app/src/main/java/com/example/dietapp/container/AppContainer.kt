package com.example.dietapp.container

import android.content.Context
import com.example.dietapp.data.DietSettings
import com.example.dietapp.database.AppDatabase
import com.example.dietapp.repository.DayRepository
import com.example.dietapp.repository.DayRepositoryImpl
import com.example.dietapp.repository.DietSettingsRepository
import com.example.dietapp.repository.DietSettingsRepositoryImpl
import com.example.dietapp.repository.DishRepository
import com.example.dietapp.repository.DishRepositoryImpl
import com.example.dietapp.repository.IngredientRepository
import com.example.dietapp.repository.IngredientRepositoryImpl

interface AppContainer {
    val ingredientRepository: IngredientRepository
    val dishRepository: DishRepository
    val dayRepository: DayRepository
    val dietSettingsRepository: DietSettingsRepository

}

class AppDataContainer(private val context: Context) : AppContainer {
    override val ingredientRepository: IngredientRepository by lazy {
        IngredientRepositoryImpl(AppDatabase.getDatabase(context).ingredientDao())
    }
    override val dishRepository: DishRepository by lazy {
        DishRepositoryImpl(AppDatabase.getDatabase(context).dishDao())
    }
    override val dayRepository: DayRepository by lazy {
        DayRepositoryImpl(AppDatabase.getDatabase(context).dayDao())
    }
    override val dietSettingsRepository: DietSettingsRepository by lazy {
        DietSettingsRepositoryImpl(AppDatabase.getDatabase(context).dietSettingsDao())
    }

}
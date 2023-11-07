package com.example.dietapp.container

import android.content.Context
import com.example.dietapp.database.AppDatabase
import com.example.dietapp.repository.DishRepository
import com.example.dietapp.repository.DishRepositoryImpl
import com.example.dietapp.repository.IngredientRepository
import com.example.dietapp.repository.IngredientRepositoryImpl

interface AppContainer {
    val ingredientRepository: IngredientRepository
    val dishRepository: DishRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val ingredientRepository: IngredientRepository by lazy {
        IngredientRepositoryImpl(AppDatabase.getDatabase(context).ingredientDao())
    }
    override val dishRepository: DishRepository by lazy {
        DishRepositoryImpl(AppDatabase.getDatabase(context).dishDao())
    }
        
}
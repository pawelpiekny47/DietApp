package com.example.dietapp.container

import android.content.Context
import com.example.dietapp.database.IngredientDatabase
import com.example.dietapp.repository.IngredientRepository
import com.example.dietapp.repository.IngredientRepositoryImpl

interface AppContainer {
    val ingredientRepository: IngredientRepository
}

class AppDataContainer(private val context: Context) : AppContainer {
    override val ingredientRepository: IngredientRepository by lazy {
        IngredientRepositoryImpl(IngredientDatabase.getDatabase(context).ingredientDao())
    }
}
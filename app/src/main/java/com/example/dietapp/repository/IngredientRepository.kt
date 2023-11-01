package com.example.dietapp.repository

import com.example.dietapp.data.Ingredient
import kotlinx.coroutines.flow.Flow

interface IngredientRepository {
    fun getAll(): Flow<List<Ingredient>>
    suspend fun insertItem(ingredient: Ingredient)
    suspend fun delete(ingredient: Ingredient)

}
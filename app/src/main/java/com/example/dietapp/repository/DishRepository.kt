package com.example.dietapp.repository

import com.example.dietapp.data.DishWithIngredients
import com.example.dietapp.data.Ingredient
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun getAll(): Flow<List<DishWithIngredients>>
}
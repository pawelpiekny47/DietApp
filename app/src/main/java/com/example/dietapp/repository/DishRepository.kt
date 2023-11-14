package com.example.dietapp.repository

import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithIngredients
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun getAll(): Flow<List<DishWithIngredients>>

    suspend fun saveDish(dish: Dish)

    suspend fun saveAll(dishIngredientCrossRefList: List<DishIngredientCrossRef>)
    suspend fun deleteAll(dishIngredientCrossRefList: List<DishIngredientCrossRef>)
}
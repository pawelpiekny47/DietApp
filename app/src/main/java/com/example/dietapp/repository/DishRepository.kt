package com.example.dietapp.repository

import com.example.dietapp.data.DayWithDishes
import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithAmount
import com.example.dietapp.data.DishWithIngredients
import kotlinx.coroutines.flow.Flow

interface DishRepository {
    fun getAll(): Flow<List<DishWithIngredients>>
    fun getDishWithAmount(id: Int): Flow<DishWithIngredients>
    fun getAllBaseDish(): Flow<List<DishWithIngredients>>
    fun getAllVariantDish(): Flow<List<DishWithIngredients>>
    suspend fun saveDish(dish: Dish): Long
    suspend fun upsertDish(dish: Dish)
    suspend fun deleteDish(dish: Dish)
    suspend fun deleteDish(id: Int)
    suspend fun saveAll(dishIngredientCrossRefList: List<DishIngredientCrossRef>)
    suspend fun saveOneCrossRef(dishIngredientCrossRefList: DishIngredientCrossRef)
    suspend fun deleteAll(dishIngredientCrossRefList: List<DishIngredientCrossRef>)
    suspend fun deleteAllCrossRefForDishId(dishId: Int)
}
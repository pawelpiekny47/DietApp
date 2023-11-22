package com.example.dietapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {
    @Transaction
    @Query("SELECT * FROM dish WHERE dishId = dishId")
    fun getAll(): Flow<List<DishWithIngredients>>

    @Upsert
    fun saveDish(dish: Dish)

    @Delete
    fun deleteDish(dish: Dish)

    @Upsert
    fun saveAll(dishIngredientCrossRef: List<DishIngredientCrossRef>)

    @Delete
    fun deleteAll(dishIngredientCrossRef: List<DishIngredientCrossRef>)

}
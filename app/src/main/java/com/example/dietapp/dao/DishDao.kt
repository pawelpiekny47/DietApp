package com.example.dietapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
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

    @Transaction
    @Query("SELECT * FROM dish WHERE dishId = dishId AND baseDish = 1")
    fun getAllBaseDish(): Flow<List<DishWithIngredients>>

    @Transaction
    @Query("SELECT * FROM dish WHERE dishId = dishId AND baseDish = 0")
    fun getAllVariantDish(): Flow<List<DishWithIngredients>>

    @Query("SELECT * FROM dish WHERE dishId = :id")
    fun getDishWithAmount(id: Int): Flow<DishWithIngredients>

    @Insert
    fun insertDish(dish: Dish): Long

    @Upsert
    fun upsertDish(dish: Dish)

    @Delete
    fun deleteDish(dish: Dish)

    @Query("DELETE FROM dish WHERE dishId = :dishId")
    fun deleteByUserId(dishId: Int)

    @Upsert
    fun saveAll(dishIngredientCrossRef: List<DishIngredientCrossRef>)

    @Upsert
    fun saveOneCrossRef(dishIngredientCrossRef: DishIngredientCrossRef)

    @Delete
    fun deleteAll(dishIngredientCrossRef: List<DishIngredientCrossRef>)

    @Query("DELETE FROM dish_ingredient_cross_ref WHERE dishId = :dishId")
    fun deleteAllCrossRefForDishId(dishId: Int)

}
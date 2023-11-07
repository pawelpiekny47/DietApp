package com.example.dietapp.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.dietapp.data.DishWithIngredients
import kotlinx.coroutines.flow.Flow

@Dao
interface DishDao {
    @Query("SELECT * FROM dish")
    fun getAll(): Flow<List<DishWithIngredients>>
}
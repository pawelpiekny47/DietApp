package com.example.dietapp.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.dietapp.data.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredients")
    fun getAll(): Flow<List<Ingredient>>

    @Insert
    suspend fun insert(ingredient: Ingredient)
}
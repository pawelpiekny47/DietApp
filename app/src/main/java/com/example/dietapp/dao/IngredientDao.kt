package com.example.dietapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.example.dietapp.data.Ingredient
import kotlinx.coroutines.flow.Flow

@Dao
interface IngredientDao {
    @Query("SELECT * FROM ingredient")
    fun getAll(): Flow<List<Ingredient>>

    @Insert
    suspend fun insert(ingredient: Ingredient)

    @Update
    suspend fun update(ingredient: Ingredient)

    @Delete
    suspend fun delete(ingredient: Ingredient)


}
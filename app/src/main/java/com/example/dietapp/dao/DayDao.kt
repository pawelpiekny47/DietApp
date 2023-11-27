package com.example.dietapp.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.dietapp.data.Day
import com.example.dietapp.data.DayDishCrossRef
import com.example.dietapp.data.DayWithDishes
import kotlinx.coroutines.flow.Flow

@Dao
interface DayDao {

    @Transaction
    @Query("SELECT * FROM day")
    fun getAll(): Flow<List<DayWithDishes>>

    @Upsert
    fun upsertDay(day: Day): Long

    @Upsert
    fun saveAll(dayDishCrossRefList: List<DayDishCrossRef>)

    @Delete
    fun deleteAll(dayDishCrossRefList: List<DayDishCrossRef>)
}
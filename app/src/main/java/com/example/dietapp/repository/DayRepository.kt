package com.example.dietapp.repository

import com.example.dietapp.data.Day
import com.example.dietapp.data.DayDishCrossRef
import com.example.dietapp.data.DayWithDishes

import kotlinx.coroutines.flow.Flow

interface DayRepository {
    fun getAll(): Flow<List<DayWithDishes>>
    suspend fun saveDay(day: Day): Long
    suspend fun deleteDay(day: Day)
    suspend fun saveAll(dayDishCrossRefList: List<DayDishCrossRef>)
    suspend fun deleteAll(dayDishCrossRefList: List<DayDishCrossRef>)

}
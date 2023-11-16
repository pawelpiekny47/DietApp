package com.example.dietapp.repository

import com.example.dietapp.dao.DayDao
import com.example.dietapp.data.Day
import com.example.dietapp.data.DayDishCrossRef
import com.example.dietapp.data.DayWithDishes
import kotlinx.coroutines.flow.Flow

class DayRepositoryImpl(private val dayDao: DayDao) : DayRepository {
    override fun getAll(): Flow<List<DayWithDishes>> = dayDao.getAll()
    override suspend fun saveDay(day: Day) = dayDao.saveDay(day)
    override suspend fun saveAll(dayDishCrossRefList: List<DayDishCrossRef>) =
        dayDao.saveAll(dayDishCrossRefList)

    override suspend fun deleteAll(dayDishCrossRefList: List<DayDishCrossRef>) =
        dayDao.deleteAll(dayDishCrossRefList)
}
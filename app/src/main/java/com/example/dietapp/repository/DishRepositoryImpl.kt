package com.example.dietapp.repository

import com.example.dietapp.dao.DishDao
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithIngredients
import kotlinx.coroutines.flow.Flow

class DishRepositoryImpl(private val dishDao: DishDao) : DishRepository {
    override fun getAll(): Flow<List<DishWithIngredients>> = dishDao.getAll()
    override suspend fun saveAll(dishIngredientCrossRefList: List<DishIngredientCrossRef>) =
        dishDao.saveAll(dishIngredientCrossRefList)

    override suspend fun deleteAll(dishIngredientCrossRefList: List<DishIngredientCrossRef>) =
        dishDao.deleteAll(dishIngredientCrossRefList)
}
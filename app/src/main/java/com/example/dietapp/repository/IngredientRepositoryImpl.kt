package com.example.dietapp.repository

import com.example.dietapp.dao.IngredientDao
import com.example.dietapp.data.Ingredient
import kotlinx.coroutines.flow.Flow

class IngredientRepositoryImpl(private val ingredientDao: IngredientDao) : IngredientRepository {
    override fun getAll(): Flow<List<Ingredient>> = ingredientDao.getAll()

    override suspend fun insertItem(ingredient: Ingredient) = ingredientDao.insert(ingredient)
}
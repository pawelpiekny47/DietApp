package com.example.dietapp.repository

import com.example.dietapp.dao.IngredientDao
import com.example.dietapp.data.Ingredient
import kotlinx.coroutines.flow.Flow

class IngredientRepositoryImpl(private val ingredientDao: IngredientDao) : IngredientRepository {
    override fun getAll(): Flow<List<Ingredient>> = ingredientDao.getAll()

    override suspend fun insertItem(ingredient: Ingredient) = ingredientDao.insert(ingredient)

    override suspend fun delete(ingredient: Ingredient) = ingredientDao.delete(ingredient)
    override suspend fun updateItem(ingredient: Ingredient) = ingredientDao.update(ingredient)

}
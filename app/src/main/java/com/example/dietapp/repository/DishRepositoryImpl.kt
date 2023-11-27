package com.example.dietapp.repository

import com.example.dietapp.dao.DishDao
import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithIngredients
import kotlinx.coroutines.flow.Flow

class DishRepositoryImpl(private val dishDao: DishDao) : DishRepository {
    override fun getAll(): Flow<List<DishWithIngredients>> = dishDao.getAll()
    override fun getAllBaseDish(): Flow<List<DishWithIngredients>> = dishDao.getAllBaseDish()
    override fun getDishWithAmount(id: Int): Flow<DishWithIngredients> = dishDao.getDishWithAmount(id)
    override fun getAllVariantDish(): Flow<List<DishWithIngredients>> = dishDao.getAllVariantDish()
    override suspend fun saveDish(dish: Dish): Long = dishDao.insertDish(dish)
    override suspend fun upsertDish(dish: Dish) = dishDao.upsertDish(dish)
    override suspend fun deleteDish(dish: Dish) = dishDao.deleteDish(dish)
    override suspend fun deleteDish(id: Int) = dishDao.deleteByUserId(id)

    override suspend fun saveAll(dishIngredientCrossRefList: List<DishIngredientCrossRef>) =
        dishDao.saveAll(dishIngredientCrossRefList)

    override suspend fun saveOneCrossRef(dishIngredientCrossRefList: DishIngredientCrossRef) =
        dishDao.saveOneCrossRef(dishIngredientCrossRefList)

    override suspend fun deleteAll(dishIngredientCrossRefList: List<DishIngredientCrossRef>) =
        dishDao.deleteAll(dishIngredientCrossRefList)

    override suspend fun deleteAllCrossRefForDishId(dishId: Int) = dishDao.deleteAllCrossRefForDishId(dishId)
}
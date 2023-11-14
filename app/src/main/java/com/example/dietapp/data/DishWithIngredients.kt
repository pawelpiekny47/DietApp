package com.example.dietapp.data

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["dishId", "ingredientId"], tableName = "dish_ingredient_cross_ref")
data class DishIngredientCrossRef (
    val dishId: Int,
    val ingredientId: Int,
    val amount: Double
)

data class DishWithIngredients(
    @Embedded val dish: Dish,
    @Relation(
        parentColumn = "dishId",
        entityColumn = "ingredientId",
        associateBy = Junction(DishIngredientCrossRef::class)
    )
    val ingredientList: List<IngredientWithAmount>
)

@DatabaseView(
    "SELECT i.ingredientId, i.name, i.fats, i.protein, i.fiber, i.carbohydrates, i.foodCategory, i.polyunsaturatedFats, i.soil, d.amount FROM Ingredient i " +
            "INNER JOIN dish_ingredient_cross_ref d ON i.ingredientId = d.ingredientId"
)
data class IngredientWithAmount(
    @Embedded val ingredient: Ingredient,
    val amount: Double
)
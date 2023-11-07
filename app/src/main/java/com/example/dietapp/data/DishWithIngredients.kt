package com.example.dietapp.data

import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.Relation

@Entity(primaryKeys = ["dishId", "ingredientId"])
data class DishIngredientCrossRef (
    val dishId: Int,
    val ingredientId: Int,
)
data class DishWithIngredients(
    @Embedded val dish: Dish,
    @Relation(
        parentColumn = "dishId",
        entityColumn = "ingredientId",
        associateBy = Junction(DishIngredientCrossRef::class)
    )
    val ingredientList: List<Ingredient>
)
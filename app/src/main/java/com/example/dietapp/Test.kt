package com.example.dietapp

import com.example.dietapp.data.FoodCategory
import com.example.dietapp.data.Ingredient
import java.math.BigDecimal

object Test {
    val ingredient1 = Ingredient(
        1,
        "1",
        BigDecimal(20),
        BigDecimal(30),
        BigDecimal(40),
        BigDecimal(50),
        BigDecimal(60),
        BigDecimal(70),
        FoodCategory.Fruit
    )
    val ingredient2 = Ingredient(
        1,
        "2",
        BigDecimal(21),
        BigDecimal(31),
        BigDecimal(41),
        BigDecimal(51),
        BigDecimal(61),
        BigDecimal(71),
        FoodCategory.Vegetable
    )
    val ingredient3 = Ingredient(
        1,
        "3",
        BigDecimal(22),
        BigDecimal(32),
        BigDecimal(42),
        BigDecimal(52),
        BigDecimal(62),
        BigDecimal(72),
        FoodCategory.ProteinSource
    )
    val ingredientsList = mutableListOf<Ingredient>(ingredient1, ingredient2, ingredient3)
}
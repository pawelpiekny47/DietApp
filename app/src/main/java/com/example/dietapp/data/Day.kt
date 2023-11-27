package com.example.dietapp.data

import androidx.room.DatabaseView
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.Junction
import androidx.room.PrimaryKey
import androidx.room.Relation

@Entity(tableName = "day")
data class Day(
    @PrimaryKey(autoGenerate = true)
    val dayId: Int = 0,

    val name: String,
)

@Entity(primaryKeys = ["dayId", "dishId"], tableName = "day_dish_cross_ref")
data class DayDishCrossRef(
    val dayId: Int,
    val dishId: Int,
    val amount: Int
)

data class DayWithDishes(
    @Embedded val day: Day,
    @Relation(
        parentColumn = "dayId",
        entityColumn = "dayId",
        associateBy = Junction(DayDishCrossRef::class)
    )
    val dishWithAmountList: Set<DishWithAmount>
)

@DatabaseView(
    "SELECT dish.dishId, dish.name, dish.description, dish.baseDish, day.amount, day.dayId FROM Dish dish " +
            "INNER JOIN day_dish_cross_ref day ON dish.dishId = day.dishId WHERE baseDish = 0"
)
data class DishWithAmount(
    val dayId: Int,
    @Embedded val dishWithIngredients: DishWithIngredients,
    val amount: Int
)
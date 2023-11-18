package com.example.dietapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dietapp.dao.DayDao
import com.example.dietapp.dao.DietSettingsDao
import com.example.dietapp.dao.DishDao
import com.example.dietapp.dao.IngredientDao
import com.example.dietapp.data.Day
import com.example.dietapp.data.DayDishCrossRef
import com.example.dietapp.data.DietSettings
import com.example.dietapp.data.Dish
import com.example.dietapp.data.DishIngredientCrossRef
import com.example.dietapp.data.DishWithAmount
import com.example.dietapp.data.Ingredient
import com.example.dietapp.data.IngredientWithAmount

@Database(
    entities = [Ingredient::class, Dish::class, DishIngredientCrossRef::class, Day::class, DayDishCrossRef::class, DietSettings::class],
    views = [IngredientWithAmount::class, DishWithAmount::class], version = 1, exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao
    abstract fun dishDao(): DishDao
    abstract fun dayDao(): DayDao
    abstract fun dietSettingsDao(): DietSettingsDao

    companion object {
        @Volatile
        private var Instance: AppDatabase? = null

        fun getDatabase(context: Context): AppDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    AppDatabase::class.java,
                    "DietApp_database"
                )
                    .createFromAsset("DietApp_database.db")
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

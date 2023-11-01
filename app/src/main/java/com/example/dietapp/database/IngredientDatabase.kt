package com.example.dietapp.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.dietapp.dao.IngredientDao
import com.example.dietapp.data.Ingredient

@Database(entities = [Ingredient::class], version = 1, exportSchema = false)
abstract class IngredientDatabase : RoomDatabase() {
    abstract fun ingredientDao(): IngredientDao

    companion object {
        @Volatile
        private var Instance: IngredientDatabase? = null

        fun getDatabase(context: Context): IngredientDatabase {
            // if the Instance is not null, return it, otherwise create a new database instance.
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(
                    context,
                    IngredientDatabase::class.java,
                    "DietApp_database"
                )
                    .build()
                    .also { Instance = it }
            }
        }
    }
}

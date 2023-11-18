package com.example.dietapp.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import com.example.dietapp.data.DietSettings
import kotlinx.coroutines.flow.Flow


@Dao
interface DietSettingsDao {
    @Transaction
    @Query("SELECT * FROM diet_settings WHERE dietSettingsId = :dietSettingsId")
    fun getDietSettings(dietSettingsId: Int): Flow<DietSettings>

    @Upsert
    fun saveDietSettings(dietSettings: DietSettings)
}
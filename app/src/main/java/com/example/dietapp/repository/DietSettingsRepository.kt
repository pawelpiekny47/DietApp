package com.example.dietapp.repository

import com.example.dietapp.data.DietSettings
import kotlinx.coroutines.flow.Flow

interface DietSettingsRepository {
    fun getDietSettings(): Flow<DietSettings>?

    suspend fun saveDietSettings(dietSettings: DietSettings)

}
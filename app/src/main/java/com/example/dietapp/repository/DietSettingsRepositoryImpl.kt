package com.example.dietapp.repository

import com.example.dietapp.dao.DietSettingsDao
import com.example.dietapp.data.DietSettings
import kotlinx.coroutines.flow.Flow

class DietSettingsRepositoryImpl(private val dietSettingsDao: DietSettingsDao) :
    DietSettingsRepository {
    override fun getDietSettings(): Flow<DietSettings> = dietSettingsDao.getDietSettings(1)

override suspend fun saveDietSettings(dietSettings: DietSettings) =
    dietSettingsDao.saveDietSettings(dietSettings)
}
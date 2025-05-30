package com.example.common_kotlin.data.source.local

import kotlinx.coroutines.flow.Flow

interface PreferencesManager {
    fun isDarkModeFlow(): Flow<Boolean>
    suspend fun setDarkMode(isDarkMode: Boolean)
}
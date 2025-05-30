package com.example.common_kotlin.base.datastore


import kotlinx.coroutines.flow.Flow


interface PreferencesDataSource  {
    fun isDarkModeFlow(): Flow<Boolean>
    suspend fun setDarkMode(isDarkMode: Boolean)
}
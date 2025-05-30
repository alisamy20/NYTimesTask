package com.example.common_kotlin.base.datastore


import kotlinx.coroutines.flow.Flow


interface Preferences {
    fun isDarkModeFlow(): Flow<Boolean>
    suspend fun setDarkMode(isDarkMode: Boolean)
}
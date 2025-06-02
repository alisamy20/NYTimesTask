package com.example.database.datastore


import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.example.common_kotlin.utils.Constant.IS_DARK_MODE
import com.example.common_kotlin.data.source.local.PreferencesManager
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class PreferencesManagerImpl @Inject constructor(
    private val dataStore: DataStore<Preferences>
) : PreferencesManager {

    val isDarkModePref = booleanPreferencesKey(IS_DARK_MODE)


    override fun isDarkModeFlow(): Flow<Boolean> {
        return dataStore.data.map { prefs ->
            prefs[isDarkModePref] == true
        }
    }

    override suspend fun setDarkMode(isDarkMode: Boolean) {
        dataStore.edit { prefs ->
            prefs[isDarkModePref] = isDarkMode
        }
    }


}
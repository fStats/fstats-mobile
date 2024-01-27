package dev.syoritohatsuki.fstatsmobile

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class StoreUserToken(private val context: Context) {
    companion object {
        private val Context.dataStorage: DataStore<Preferences> by preferencesDataStore("userToken")
        val USER_TOKEN_KEY = stringPreferencesKey("user_token")
    }

    val getToken: Flow<String?> = context.dataStorage.data.map { preferences ->
        preferences[USER_TOKEN_KEY] ?: ""
    }

    suspend fun saveToken(name: String) {
        context.dataStorage.edit { preferences ->
            preferences[USER_TOKEN_KEY] = name
        }
    }
}
package ru.stroesku.kmm.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch
import timber.log.Timber

class TokenPreferences(context: Context, val scope: CoroutineScope) :
    BasePreferences(LOCAL_STORAGE, context) {
    companion object {
        private const val LOCAL_STORAGE = "user_preferences"
        val TOKEN = stringPreferencesKey("b_token")
    }

    fun setToken(type: String, token: String) {
        scope.launch {
            context.dataStore.edit { preferences ->
                val oldToken = preferences[TOKEN] ?: ""
                val newToken = "$type $token"
                if (oldToken != newToken) {
                    preferences[TOKEN] = newToken
                    Timber.e("Old token: $oldToken replace to new token: $newToken")
                } else Timber.e("Old and new token was identical")
            }
        }
    }

    suspend fun getCurrentToken(): String {
        return getDataStore().data
            .map { it[TOKEN] ?: "" }
            .first()
    }

    suspend fun removeToken() {
        context.dataStore.edit { preferences ->
            Timber.e("Token ${preferences[TOKEN]} removed")
            preferences[TOKEN] = ""
        }
    }
}
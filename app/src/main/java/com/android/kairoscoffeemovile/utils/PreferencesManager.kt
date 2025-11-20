package com.android.kairoscoffeemovile.utils

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("kairos_prefs")

class PreferencesManager(private val context: Context) {

    companion object {
        private var INSTANCE: PreferencesManager? = null

        fun getInstance(context: Context): PreferencesManager {
            return INSTANCE ?: synchronized(this) {
                val inst = PreferencesManager(context.applicationContext)
                INSTANCE = inst
                inst
            }
        }

        val KEY_TOKEN = stringPreferencesKey("jwt_token")
        val KEY_EMAIL = stringPreferencesKey("user_email")
        val KEY_ROLE = stringPreferencesKey("user_role")
        val KEY_LOGGED = booleanPreferencesKey("is_logged")
    }

    val isLoggedInFlow: Flow<Boolean> =
        context.dataStore.data.map { it[KEY_LOGGED] ?: false }

    val tokenFlow: Flow<String> =
        context.dataStore.data.map { it[KEY_TOKEN] ?: "" }

    val userRoleFlow: Flow<String> =
        context.dataStore.data.map { it[KEY_ROLE] ?: "" }

    val emailFlow: Flow<String> =
        context.dataStore.data.map { it[KEY_EMAIL] ?: "" }

    suspend fun saveLoginState(token: String, role: String, email: String) {
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGGED] = true
            prefs[KEY_TOKEN] = token
            prefs[KEY_ROLE] = role
            prefs[KEY_EMAIL] = email
        }
    }

    suspend fun logout() {
        context.dataStore.edit { prefs ->
            prefs[KEY_LOGGED] = false
            prefs[KEY_TOKEN] = ""
            prefs[KEY_ROLE] = ""
            prefs[KEY_EMAIL] = ""
        }
    }
}

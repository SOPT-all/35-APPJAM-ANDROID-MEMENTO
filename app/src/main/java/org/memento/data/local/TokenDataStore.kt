package org.memento.data.local

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.map
import org.memento.BuildConfig
import javax.inject.Inject

class TokenDataStore
    @Inject
    constructor(
        private val preferenceDataStore: DataStore<Preferences>,
    ) {
        suspend fun getAccessToken(): String? =
            preferenceDataStore.data.map { preferences ->
                preferences[preferencesTokenKey] ?: TEMPORARY_TOKEN
            }.firstOrNull()

        suspend fun setAccessToken(token: String) {
            preferenceDataStore.edit { preferences ->
                preferences[preferencesTokenKey] = token
            }
        }

        suspend fun getRefreshToken(): String? =
            preferenceDataStore.data.map { preferences ->
                preferences[preferencesRefreshTokenKey] ?: TEMPORARY_REFRESH_TOKEN
            }.firstOrNull()


        suspend fun setRefreshToken(token: String) {
            preferenceDataStore.edit { preferences ->
                preferences[preferencesRefreshTokenKey] = token
            }
        }

        suspend fun clearInfo() {
            preferenceDataStore.edit { preferences ->
                preferences.remove(preferencesTokenKey)
            }
        }

        companion object {
            private const val TOKEN_KEY = "token_key"
            private val preferencesTokenKey = stringPreferencesKey(TOKEN_KEY)

            private const val REFRESH_TOKEN_KEY = "refresh_token_key"
            private val preferencesRefreshTokenKey = stringPreferencesKey(REFRESH_TOKEN_KEY)

            private const val TEMPORARY_TOKEN = BuildConfig.ACCESS_TOKEN
            private const val TEMPORARY_REFRESH_TOKEN = BuildConfig.REFRESH_TOKEN
        }
    }

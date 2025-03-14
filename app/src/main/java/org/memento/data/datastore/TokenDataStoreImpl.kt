package org.memento.data.datastore

import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import timber.log.Timber
import javax.inject.Inject

class TokenDataStoreImpl
    @Inject
    constructor(
        private val sharedPreferences: SharedPreferences,
    ) : TokenDataStore {
        override var accessToken: String
            get() {
                val token = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
                Timber.d("TokenDataStore: Loaded accessToken = $token")
                return token
            }
            set(value) {
                Timber.d("TokenDataStore: Saving accessToken = $value")
                sharedPreferences.edit().putString(ACCESS_TOKEN, value).apply()
            }

        override var refreshToken: String
            get() {
                val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, "") ?: ""
                Timber.d("TokenDataStore: Loaded RefreshToken = $refreshToken")
                return refreshToken
            }
            set(value) {
                Timber.d("TokenDataStore: Saving RefreshToken = $value")
                sharedPreferences.edit().putString(REFRESH_TOKEN, value).apply()
            }

        override var isNewUser: Boolean
            get() {
                val isNewUser = sharedPreferences.getBoolean(IS_NEW_USER, true)
                Timber.d("TokenDataStore: Loaded isNewUser = $isNewUser")
                return isNewUser
            }
            set(value) {
                Timber.d("TokenDataStore: Saving isNewUser = $value")
                sharedPreferences.edit().putBoolean(IS_NEW_USER, value).apply()
            }

        override fun clearInfo() {
            sharedPreferences.edit().clear().apply()
        }

        companion object {
            private const val ACCESS_TOKEN = "ACCESS_TOKEN"
            private const val REFRESH_TOKEN = "REFRESH_TOKEN"
            private const val IS_NEW_USER = "IS_NEW_USER"
        }
    }

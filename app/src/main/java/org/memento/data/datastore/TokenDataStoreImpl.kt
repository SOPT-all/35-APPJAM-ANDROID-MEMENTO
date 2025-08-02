package org.memento.data.datastore

import android.content.SharedPreferences
import androidx.datastore.preferences.core.edit
import javax.inject.Inject

class TokenDataStoreImpl
    @Inject
    constructor(
        private val sharedPreferences: SharedPreferences,
    ) : TokenDataStore {
        override var accessToken: String
            get() {
                val token = sharedPreferences.getString(ACCESS_TOKEN, "") ?: ""
                return token
            }
            set(value) {
                sharedPreferences.edit().putString(ACCESS_TOKEN, value).apply()
            }

        override var refreshToken: String
            get() {
                val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, "") ?: ""
                return refreshToken
            }
            set(value) {
                sharedPreferences.edit().putString(REFRESH_TOKEN, value).apply()
            }

        override var isNewUser: Boolean
            get() {
                val isNewUser = sharedPreferences.getBoolean(IS_NEW_USER, true)
                return isNewUser
            }
            set(value) {
                sharedPreferences.edit().putBoolean(IS_NEW_USER, value).apply()
            }

        override var userEmail: String
            get() {
                val userEmail = sharedPreferences.getString(USER_EMAIL, "") ?: ""
                return userEmail
            }
            set(value) {
                sharedPreferences.edit().putString(USER_EMAIL, value).apply()
            }

        override var loginSuccess: Boolean
            get() = sharedPreferences.getBoolean(LOGIN_SUCCESS, false)
            set(value) {
                sharedPreferences.edit().putBoolean(LOGIN_SUCCESS, value).apply()
            }

        override fun clearInfo() {
            sharedPreferences.edit().clear().apply()
            sharedPreferences.edit().putBoolean(LOGIN_SUCCESS, false).apply()
        }

        companion object {
            private const val ACCESS_TOKEN = "ACCESS_TOKEN"
            private const val REFRESH_TOKEN = "REFRESH_TOKEN"
            private const val IS_NEW_USER = "IS_NEW_USER"
            private const val USER_EMAIL = "USER_EMAIL"
            private const val LOGIN_SUCCESS = "LOGIN_SUCCESS"
        }
    }

package org.memento.data.datastore

import android.content.SharedPreferences
import androidx.core.content.edit
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
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
                sharedPreferences.edit { putString(ACCESS_TOKEN, value) }
            }

        override var refreshToken: String
            get() {
                val refreshToken = sharedPreferences.getString(REFRESH_TOKEN, "") ?: ""
                return refreshToken
            }
            set(value) {
                sharedPreferences.edit { putString(REFRESH_TOKEN, value) }
            }

        override var isNewUser: Boolean
            get() {
                val isNewUser = sharedPreferences.getBoolean(IS_NEW_USER, true)
                return isNewUser
            }
            set(value) {
                sharedPreferences.edit { putBoolean(IS_NEW_USER, value) }
            }

        override var userEmail: String
            get() {
                val userEmail = sharedPreferences.getString(USER_EMAIL, "") ?: ""
                return userEmail
            }
            set(value) {
                sharedPreferences.edit { putString(USER_EMAIL, value) }
            }

        override var loginSuccess: Boolean
            get() = sharedPreferences.getBoolean(LOGIN_SUCCESS, false)
            set(value) {
                sharedPreferences.edit { putBoolean(LOGIN_SUCCESS, value) }
            }

        override val loginSuccessFlow: Flow<Boolean> = getBooleanFlow(LOGIN_SUCCESS, false)

        override var onboardingCompleted: Boolean
            get() = sharedPreferences.getBoolean(ONBOARDING_COMPLETED, false)
            set(value) {
                sharedPreferences.edit { putBoolean(ONBOARDING_COMPLETED, value) }
            }

        override val onboardingCompletedFlow: Flow<Boolean> = getBooleanFlow(ONBOARDING_COMPLETED, false)

        override fun clearInfo() {
            sharedPreferences.edit { clear() }
        }

        private fun getBooleanFlow(
            key: String,
            defaultValue: Boolean,
        ): Flow<Boolean> =
            callbackFlow {
                val listener =
                    SharedPreferences.OnSharedPreferenceChangeListener { _, changedKey ->
                        if (changedKey == key) {
                            trySend(sharedPreferences.getBoolean(key, defaultValue))
                        }
                    }
                sharedPreferences.registerOnSharedPreferenceChangeListener(listener)
                trySend(sharedPreferences.getBoolean(key, defaultValue))
                awaitClose { sharedPreferences.unregisterOnSharedPreferenceChangeListener(listener) }
            }

        companion object {
            private const val ACCESS_TOKEN = "ACCESS_TOKEN"
            private const val REFRESH_TOKEN = "REFRESH_TOKEN"
            private const val IS_NEW_USER = "IS_NEW_USER"
            private const val USER_EMAIL = "USER_EMAIL"
            private const val LOGIN_SUCCESS = "LOGIN_SUCCESS"
            private const val ONBOARDING_COMPLETED = "ONBOARDING_COMPLETED"
        }
    }

package org.memento.data.datastore

import kotlinx.coroutines.flow.Flow

interface TokenDataStore {
    var accessToken: String
    var refreshToken: String
    var isNewUser: Boolean
    var userEmail: String
    var loginSuccess: Boolean
    val loginSuccessFlow: Flow<Boolean>
    var onboardingCompleted: Boolean
    val onboardingCompletedFlow: Flow<Boolean>

    fun clearSession()

    fun clearAllInfo()
}

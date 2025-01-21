package org.memento.di

import kotlinx.coroutines.runBlocking
import okhttp3.Authenticator
import okhttp3.Request
import okhttp3.Response
import okhttp3.Route
import org.memento.data.local.TokenDataStore
import org.memento.domain.repository.LoginRepository
import javax.inject.Inject

class TokenAuthenticator @Inject constructor(
    private val tokenDataStore: TokenDataStore,
    private val loginRepository: LoginRepository,
) : Authenticator {

    override fun authenticate(route: Route?, response: Response): Request? {
        if (response.code == 401) {
            val refreshToken = runBlocking { tokenDataStore.getRefreshToken() }
            // token authenticator 401 구현 필요
        }
        return null
    }
}

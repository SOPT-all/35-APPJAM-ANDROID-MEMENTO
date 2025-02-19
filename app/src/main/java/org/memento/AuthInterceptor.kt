package org.memento

import kotlinx.coroutines.Deferred
import kotlinx.coroutines.async
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.sync.Mutex
import kotlinx.coroutines.sync.withLock
import okhttp3.Interceptor
import okhttp3.Request
import okhttp3.Response
import okio.IOException
import org.memento.data.datastore.TokenDataStore
import timber.log.Timber
import javax.inject.Inject
import javax.inject.Provider

class AuthInterceptor
    @Inject
    constructor(
        private val tokenDataStore: TokenDataStore,
        private val tokenManagerProvider: Provider<TokenManager>,
    ) : Interceptor {
        val mutex = Mutex()
        var tokenRefreshJob: Deferred<Boolean>? = null

        override fun intercept(chain: Interceptor.Chain): Response {
            val request = chain.request()
            val url = request.url.toString()

            return if (shouldAddAccessToken(url)) {
                val response = proceedWithAuthorization(chain, request)
                if (response.code == TOKEN_EXPIRED_CODE) {
                    response.close()
                    return handleTokenExpiration(chain, request)
                }
                response
            } else {
                chain.proceed(request)
            }
        }

        private fun shouldAddAccessToken(url: String): Boolean {
            return !url.contains("/api/v1/auth/login") &&
                !url.contains("/api/v1/auth/token/refresh")
        }

        private fun addAuthorizationHeader(request: Request): Request {
            return request.newBuilder()
                .header(AUTHORIZATION, "$BEARER ${tokenDataStore.accessToken}")
                .build()
        }

        private fun proceedWithAuthorization(
            chain: Interceptor.Chain,
            request: Request,
        ): Response {
            val authRequest = addAuthorizationHeader(request)
            return chain.proceed(authRequest)
        }

        private fun handleTokenExpiration(
            chain: Interceptor.Chain,
            request: Request,
        ): Response {
            return runBlocking {
                mutex.withLock {
                    if (tokenRefreshJob?.isCompleted != false) {
                        tokenRefreshJob =
                            async {
                                tryReissueToken()
                            }
                    }
                }
                val tokenRefreshed = tokenRefreshJob?.await() ?: false

                if (tokenRefreshed) {
                    proceedWithAuthorization(chain, request)
                } else {
                    clearUserInfoAndNavigateToLogin()
                    throw IOException("Token expired and reissue failed")
                }
            }
        }

        private fun tryReissueToken(): Boolean {
            val reissueTokenRepository = tokenManagerProvider.get()
            return try {
                runBlocking {
                    reissueTokenRepository.postRefreshToken().onSuccess { data ->
                        Timber.d("tryReissueToken : Token : ${data.accessToken}")
                        Timber.d("tryReissueToken : RefreshToken : ${data.refreshToken}")
                        if (data.accessToken.isEmpty() || data.refreshToken.isEmpty()) {
                            Timber.e("Token reissue returned empty tokens")
                            clearUserInfoAndNavigateToLogin()
                        } else {
                            updateToken(data.accessToken, data.refreshToken)
                        }
                    }.onFailure { exception: Throwable ->
                        Timber.e("Token reissue failed: ${exception.message}")
                        Timber.e("failed but checking : token : ${tokenDataStore.accessToken}")
                        Timber.e("failed but checking : refreshtoken : ${tokenDataStore.refreshToken}")
                        clearUserInfoAndNavigateToLogin()
                    }
                }
                true
            } catch (t: Throwable) {
                Timber.e("Unexpected error during token reissue: ${t.message}")
                clearUserInfoAndNavigateToLogin()
                false
            }
        }

        private fun updateToken(
            newAccessToken: String,
            newRefreshToken: String,
        ) {
            Timber.d("New Access Token: $newAccessToken")
            Timber.d("New Refresh Token: $newRefreshToken")
            tokenDataStore.apply {
                accessToken = newAccessToken
                refreshToken = newRefreshToken
            }
        }

        private fun clearUserInfoAndNavigateToLogin() {
            tokenDataStore.clearInfo()
            // todo : 로그인 화면으로 이동
        }

        companion object {
            private const val TOKEN_EXPIRED_CODE = 401
            private const val BEARER = "Bearer"
            private const val AUTHORIZATION = "Authorization"
        }
    }

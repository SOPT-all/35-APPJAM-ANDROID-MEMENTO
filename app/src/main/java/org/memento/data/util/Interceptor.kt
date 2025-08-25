package org.memento.data.util

import kotlinx.coroutines.runBlocking
import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.memento.BuildConfig
import org.memento.core.event.EventBus
import org.memento.data.datastore.TokenDataStore
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseRefreshDto
import org.memento.presentation.type.EventType
import timber.log.Timber
import javax.inject.Inject

class Interceptor
    @Inject
    constructor(
        private val json: Json,
        private val eventBus: EventBus,
        private val tokenDataStore: TokenDataStore,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            val authRequest =
                if (!tokenDataStore.isNewUser) {
                    originalRequest.newAuthBuilder()
                } else {
                    originalRequest
                }

            val response = chain.proceed(authRequest)

            if (response.code == TOKEN_EXPIRED_CODE) {
                response.close()

                synchronized(this) {
                    val isRefreshSuccessful = refreshToken(chain)

                    if (isRefreshSuccessful) {
                        return chain.proceed(originalRequest.newAuthBuilder())
                    }
                }
                throw IOException("Session expired. Please log in again.")
            }
            return response
        }

        private fun refreshToken(chain: Interceptor.Chain): Boolean {
            Timber.d("⏳ Token expired, trying to refresh token.")

            val currentRefreshToken = tokenDataStore.refreshToken
            if (currentRefreshToken.isBlank()) {
                handleSessionExpired()
                return false
            }

            val refreshTokenRequest =
                chain.request().newBuilder()
                    .url("${BuildConfig.BASE_URL}api/v1/auth/token/refresh")
                    .post("{}".toRequestBody("application/json".toMediaType()))
                    .addHeader(AUTHORIZATION, "$BEARER ${tokenDataStore.refreshToken}")
                    .build()

            val refreshTokenResponse = chain.proceed(refreshTokenRequest)

            if (refreshTokenResponse.isSuccessful) {
                Timber.d("✅ Token refresh successful.")
                val responseBodyString = refreshTokenResponse.peekBody(Long.MAX_VALUE).string()
                val responseRefresh = json.decodeFromString<BaseResponse<ResponseRefreshDto>>(responseBodyString)

                responseRefresh.data?.let {
                    with(tokenDataStore) {
                        accessToken = responseRefresh.data.accessToken
                        refreshToken = responseRefresh.data.refreshToken
                    }
                }
                Timber.d("🎉 Updated accessToken")

                refreshTokenResponse.close()
                return true
            } else {
                Timber.d("❌ Failed to refresh token, response: ${refreshTokenResponse.code}")
                refreshTokenResponse.close()
                handleSessionExpired()
                return false
            }
        }

        private fun handleSessionExpired() {
            runBlocking {
                eventBus.emit(EventType.TokenExpired)
            }
        }

        private fun Request.newAuthBuilder() = this.newBuilder().addHeader(AUTHORIZATION, "$BEARER ${tokenDataStore.accessToken}").build()

        companion object {
            private const val TOKEN_EXPIRED_CODE = 401
            private const val BEARER = "Bearer"
            private const val AUTHORIZATION = "Authorization"
        }
    }

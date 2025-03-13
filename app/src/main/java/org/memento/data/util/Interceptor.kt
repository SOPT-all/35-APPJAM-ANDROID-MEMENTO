package org.memento.data.util

import kotlinx.serialization.json.Json
import okhttp3.Interceptor
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.Request
import okhttp3.RequestBody.Companion.toRequestBody
import okhttp3.Response
import okio.IOException
import org.memento.BuildConfig
import org.memento.data.datastore.TokenDataStore
import org.memento.data.dto.BaseResponse
import org.memento.data.dto.response.ResponseRefreshDto
import timber.log.Timber
import javax.inject.Inject

class Interceptor
    @Inject
    constructor(
        private val json: Json,
        private val tokenDataStore: TokenDataStore,
    ) : Interceptor {
        override fun intercept(chain: Interceptor.Chain): Response {
            val originalRequest = chain.request()

            Timber.d("🔍 Original Request: ${originalRequest.url}")

            val authRequest =
                if (!tokenDataStore.isNewUser) {
                    Timber.d("🔑 Using access token: ${tokenDataStore.accessToken}")
                    originalRequest.newAuthBuilder()
                } else {
                    Timber.d("❌ No access token found.")
                    originalRequest
                }

            val response = chain.proceed(authRequest)

            Timber.d("📬 Response received with status code: ${response.code}")

            when (response.code) {
                TOKEN_EXPIRED_CODE -> {
                    Timber.d("⏳ Token expired, trying to refresh token.")
                    Timber.d("🔑 Current refreshToken: ${tokenDataStore.refreshToken}")

                    val refreshTokenRequest =
                        originalRequest.newBuilder()
                            .url("${BuildConfig.BASE_URL}/api/v1/auth/token/refresh")
                            .post("{}".toRequestBody("application/json".toMediaType()))
                            .addHeader(AUTHORIZATION, "$BEARER ${tokenDataStore.refreshToken}")
                            .build()

                    Timber.d("🔄 Sending refresh token request: ${refreshTokenRequest.url}")
                    Timber.d("🔄 Request headers: ${refreshTokenRequest.headers}")

                    response.close()

                    val refreshTokenResponse = chain.proceed(refreshTokenRequest)

                    Timber.d("📬 Response from refresh token request: ${refreshTokenResponse.code}")
                    val responseBodyString = refreshTokenResponse.peekBody(Long.MAX_VALUE).string()

                    if (refreshTokenResponse.isSuccessful) {
                        Timber.d("✅ Token refresh successful.")
                        Timber.d("🔑 Refresh token response: $responseBodyString")

                        val responseRefresh = json.decodeFromString<BaseResponse<ResponseRefreshDto>>(responseBodyString)
                        Timber.d("🔄 Parsed response: $responseRefresh")

                        Timber.d("🎉 Old accessToken: ${tokenDataStore.accessToken}")
                        Timber.d("🎉 Old refreshToken: ${tokenDataStore.refreshToken}")

                        responseRefresh.data?.let {
                            with(tokenDataStore) {
                                accessToken = responseRefresh.data.accessToken
                                refreshToken = responseRefresh.data.refreshToken
                            }
                        }

                        Timber.d("🎉 Updated accessToken: ${tokenDataStore.accessToken}")
                        Timber.d("🎉 Updated refreshToken: ${tokenDataStore.refreshToken}")

                        refreshTokenResponse.close()

                        val newRequest = originalRequest.newAuthBuilder()
                        Timber.d("🚀 Sending new request with updated access token.")

                        return chain.proceed(newRequest)
                    } else {
                        Timber.d("❌ Failed to refresh token, response: ${refreshTokenResponse.code}")
                        Timber.d("❌ Error body: $responseBodyString")
                        refreshTokenResponse.close()
                        throw IOException("Failed to refresh token")
                    }
                }
            }
            return response
        }

        private fun Request.newAuthBuilder() = this.newBuilder().addHeader(AUTHORIZATION, "$BEARER ${tokenDataStore.accessToken}").build()

        private fun clearUserInfo() {
            tokenDataStore.clearInfo()
        }

        companion object {
            private const val TOKEN_EXPIRED_CODE = 401
            private const val BEARER = "Bearer"
            private const val AUTHORIZATION = "Authorization"
        }
    }

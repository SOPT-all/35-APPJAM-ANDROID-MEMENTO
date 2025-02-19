package org.memento.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.memento.BuildConfig
import org.memento.core.util.TokenManager
import org.memento.data.datastore.TokenDataStoreImpl
import org.memento.data.util.AuthInterceptor
import org.memento.domain.repository.RefreshTokenRepository
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Provider
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CONTENT_TYPE = "application/json"
    private val BASE_URL = BuildConfig.BASE_URL

    @Singleton
    @Provides
    fun provideJson(): Json {
        return Json {
            ignoreUnknownKeys = true
            isLenient = true
            encodeDefaults = true
        }
    }

    @Provides
    @Singleton
    fun provideAuthInterceptor(
        tokenDataStore: TokenDataStoreImpl,
        tokenManager: Provider<TokenManager>,
    ): AuthInterceptor {
        return AuthInterceptor(tokenDataStore, tokenManager)
    }

    @Singleton
    @Provides
    fun provideLoggingInterceptor(): HttpLoggingInterceptor {
        return HttpLoggingInterceptor { message ->
            Timber.tag("Retrofit2").d("CONNECTION INFO -> $message")
        }.apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    @Singleton
    @Provides
    fun provideOkHttpClient(
        loggingInterceptor: HttpLoggingInterceptor,
        authInterceptor: AuthInterceptor,
    ): OkHttpClient {
        val builder =
            OkHttpClient.Builder()
                .connectTimeout(300, java.util.concurrent.TimeUnit.SECONDS) // 연결 타임아웃 30초
                .readTimeout(300, java.util.concurrent.TimeUnit.SECONDS) // 읽기 타임아웃 30초
                .writeTimeout(300, java.util.concurrent.TimeUnit.SECONDS) // 쓰기 타임아웃 30초
                .callTimeout(300, java.util.concurrent.TimeUnit.SECONDS) // 전체 호출 타임아웃 30초
        if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(authInterceptor)
        return builder.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        json: Json,
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(json.asConverterFactory(CONTENT_TYPE.toMediaType()))
            .build()
    }

    @Provides
    @Singleton
    fun provideTokenManger(
        tokenRepository: RefreshTokenRepository,
    ): TokenManager {
        return TokenManager(tokenRepository)
    }
}

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
import org.memento.data.datastore.TokenDataStore
import org.memento.data.util.Interceptor
import retrofit2.Retrofit
import timber.log.Timber
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    private const val CONTENT_TYPE = "application/json"
    private const val BASE_URL = BuildConfig.BASE_URL

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
    fun provideInterceptor(
        json: Json,
        tokenDataStore: TokenDataStore,
    ): Interceptor {
        return Interceptor(json, tokenDataStore)
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
        interceptor: Interceptor,
    ): OkHttpClient {
        val builder =
            OkHttpClient.Builder()
                .connectTimeout(300, java.util.concurrent.TimeUnit.SECONDS) // 연결 타임아웃 30초
                .readTimeout(300, java.util.concurrent.TimeUnit.SECONDS) // 읽기 타임아웃 30초
                .writeTimeout(300, java.util.concurrent.TimeUnit.SECONDS) // 쓰기 타임아웃 30초
                .callTimeout(300, java.util.concurrent.TimeUnit.SECONDS) // 전체 호출 타임아웃 30초
        if (BuildConfig.DEBUG) builder.addInterceptor(loggingInterceptor)
        builder.addInterceptor(interceptor)
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
}

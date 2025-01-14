package org.memento.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.service.ReqresService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {
    @Provides
    @Singleton
    fun provideReqresService(retrofit: Retrofit): ReqresService = retrofit.create(ReqresService::class.java)
}

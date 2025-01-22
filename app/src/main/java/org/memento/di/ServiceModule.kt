package org.memento.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.service.AddScheduleService
import org.memento.data.service.LoginService
import org.memento.data.service.ReqresService
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal object ServiceModule {
    @Provides
    @Singleton
    fun provideReqresService(retrofit: Retrofit): ReqresService = retrofit.create(ReqresService::class.java)

    @Provides
    @Singleton
    fun provideLoginService(retrofit: Retrofit): LoginService = retrofit.create(LoginService::class.java)

    @Provides
    @Singleton
    fun provideAddScheduleService(retrofit: Retrofit): AddScheduleService = retrofit.create(AddScheduleService::class.java)
}

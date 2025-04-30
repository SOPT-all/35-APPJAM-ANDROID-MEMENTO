package org.memento.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.service.AddPlanService
import org.memento.data.service.LoginService
import org.memento.data.service.ReqresService
import org.memento.data.service.ScheduleService
import org.memento.data.service.SettingService
import org.memento.data.service.TodoService
import org.memento.data.service.UserInfoUpdateService
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
    fun provideAddScheduleService(retrofit: Retrofit): AddPlanService = retrofit.create(AddPlanService::class.java)

    @Provides
    @Singleton
    fun provideScheduleService(retrofit: Retrofit): ScheduleService = retrofit.create(ScheduleService::class.java)

    @Provides
    @Singleton
    fun provideUserInfoUpdateService(retrofit: Retrofit): UserInfoUpdateService = retrofit.create(UserInfoUpdateService::class.java)

    @Provides
    @Singleton
    fun provideTodoService(retrofit: Retrofit): TodoService = retrofit.create(TodoService::class.java)

    @Provides
    @Singleton
    fun provideSettingService(retrofit: Retrofit): SettingService = retrofit.create(SettingService::class.java)
}

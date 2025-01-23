package org.memento.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.repositoryimpl.AddPlanRepositoryImpl
import org.memento.data.repositoryimpl.LoginRepositoryImpl
import org.memento.data.repositoryimpl.ReqresRepositoryImpl
import org.memento.data.repositoryimpl.ScheduleRepositoryImpl
import org.memento.data.repositoryimpl.UserInfoUpdateRepositoryImpl
import org.memento.data.repositoryimpl.TodoRepositoryImpl
import org.memento.domain.repository.AddPlanRepository
import org.memento.domain.repository.LoginRepository
import org.memento.domain.repository.ReqresRepository
import org.memento.domain.repository.ScheduleRepository
import org.memento.domain.repository.UserInfoUpdateRepository
import org.memento.domain.repository.TodoRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsReqresRepository(reqresRepositoryImpl: ReqresRepositoryImpl): ReqresRepository

    @Binds
    @Singleton
    abstract fun bindsLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @Singleton
    abstract fun bindsAddPlanRepository(addPlanRepositoryImpl: AddPlanRepositoryImpl): AddPlanRepository

    @Binds
    @Singleton
    abstract fun bindsScheduleRepository(scheduleRepositoryImpl: ScheduleRepositoryImpl): ScheduleRepository

    @Binds
    @Singleton
    abstract fun bindsUserRepository(userInfoUpdateRepositoryImpl: UserInfoUpdateRepositoryImpl): UserInfoUpdateRepository

    @Binds
    @Singleton
    abstract fun bindsTodoRepository(todoRepositoryImpl: TodoRepositoryImpl): TodoRepository
}

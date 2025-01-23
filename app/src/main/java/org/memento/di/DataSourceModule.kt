package org.memento.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.datasource.AddPlanDataSource
import org.memento.data.datasource.LoginDataSource
import org.memento.data.datasource.ReqresDataSource
import org.memento.data.datasource.ScheduleDataSource
import org.memento.data.datasource.TodoDataSource
import org.memento.data.datasource.UserInfoDataSource
import org.memento.data.datasourceimpl.AddPlanDataSourceImpl
import org.memento.data.datasourceimpl.LoginDataSourceImpl
import org.memento.data.datasourceimpl.ReqresDataSourceImpl
import org.memento.data.datasourceimpl.ScheduleDataSourceImpl
import org.memento.data.datasourceimpl.TodoDataSourceImpl
import org.memento.data.datasourceimpl.UserDataSourceImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class DataSourceModule {
    @Binds
    @Singleton
    abstract fun bindsReqresDataSource(reqresDataSourceImpl: ReqresDataSourceImpl): ReqresDataSource

    @Binds
    @Singleton
    abstract fun bindsLoginDataSource(loginDataSourceImpl: LoginDataSourceImpl): LoginDataSource

    @Binds
    @Singleton
    abstract fun bindsAddPlanDataSource(addScheduleDataSourceImpl: AddPlanDataSourceImpl): AddPlanDataSource

    @Binds
    @Singleton
    abstract fun bindsUserDataSource(userDataSourceImpl: UserDataSourceImpl): UserInfoDataSource

    @Binds
    @Singleton
    abstract fun bindsScheduleDataSource(scheduleDataSourceImpl: ScheduleDataSourceImpl): ScheduleDataSource

    @Binds
    @Singleton
    abstract fun bindsTodoDataSource(todoDataSourceImpl: TodoDataSourceImpl): TodoDataSource
}

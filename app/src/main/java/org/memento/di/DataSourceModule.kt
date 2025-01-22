package org.memento.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.datasource.AddPlanDataSource
import org.memento.data.datasource.LoginDataSource
import org.memento.data.datasource.ReqresDataSource
import org.memento.data.datasourceimpl.AddPlanDataSourceImpl
import org.memento.data.datasourceimpl.LoginDataSourceImpl
import org.memento.data.datasourceimpl.ReqresDataSourceImpl
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
    abstract fun bindsAddScheduleDataSource(addScheduleDataSourceImpl: AddPlanDataSourceImpl): AddPlanDataSource
}

package org.memento.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.datastore.LoginDataSource
import org.memento.data.datastore.ReqresDataSource
import org.memento.data.datastroeimpl.LoginDataSourceImpl
import org.memento.data.datastroeimpl.ReqresDataSourceImpl
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
}

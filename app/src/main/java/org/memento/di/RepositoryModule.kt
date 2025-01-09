package org.memento.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.repositoryimpl.ReqresRepositoryImpl
import org.memento.domain.repository.ReqresRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {
    @Binds
    @Singleton
    abstract fun bindsReqresRepository(reqresRepositoryImpl: ReqresRepositoryImpl): ReqresRepository
}

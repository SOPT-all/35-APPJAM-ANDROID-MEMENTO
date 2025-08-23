package org.memento.di

import android.content.SharedPreferences
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import org.memento.data.datastore.TokenDataStore
import org.memento.data.datastore.TokenDataStoreImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataStoreModule {
    @Provides
    @Singleton
    fun provideDataStore(
        sharedPreferences: SharedPreferences,
    ): TokenDataStore {
        return TokenDataStoreImpl(sharedPreferences)
    }
}

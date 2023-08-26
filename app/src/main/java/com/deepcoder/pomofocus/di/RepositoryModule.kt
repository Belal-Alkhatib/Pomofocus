package com.deepcoder.pomofocus.di

import com.deepcoder.pomofocus.data.RepositoryImpl
import com.deepcoder.pomofocus.domain.Repository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {
    @Binds
    abstract fun bindsRepository(repository: RepositoryImpl): Repository
}
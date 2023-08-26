package com.deepcoder.pomofocus.di

import android.content.Context
import androidx.room.Room
import com.deepcoder.pomofocus.data.local.room.PomofocusDao
import com.deepcoder.pomofocus.data.local.room.PomofocusDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(
        @ApplicationContext context: Context,
        @Named("databaseName") databaseName: String
    ): PomofocusDatabase {
        return Room
            .databaseBuilder(context, PomofocusDatabase::class.java, databaseName)
            .build()
    }

    @Singleton
    @Provides
    @Named("databaseName")
    fun provideDataBaseName(): String = "pomofocus_app_db"

    @Provides
    @Singleton
    fun provideMarvelDao(pomofocusDatabase: PomofocusDatabase): PomofocusDao = pomofocusDatabase.getPomofocusDao()


}
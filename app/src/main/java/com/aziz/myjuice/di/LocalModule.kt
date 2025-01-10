package com.aziz.myjuice.di

import android.app.Application
import androidx.room.Room
import com.aziz.myjuice.dataroom.local.MyJuiceDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object LocalModule {
    @Provides
    @Singleton
    fun provideDatabase(application: Application) = Room
        .databaseBuilder(application, MyJuiceDatabase::class.java, "myjuice.db")
        .fallbackToDestructiveMigration()
        .build()

    @Provides
    fun provideDao(database: MyJuiceDatabase) = database.MyJuiceDao()
}
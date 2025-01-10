package com.aziz.myjuice.di

import com.aziz.myjuice.dataroom.local.MyJuiceDao
import com.aziz.myjuice.dataroom.repository.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideRepository(MyJuiceDao: MyJuiceDao) = Repository(MyJuiceDao)
}
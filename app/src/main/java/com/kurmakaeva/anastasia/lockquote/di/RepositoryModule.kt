package com.kurmakaeva.anastasia.lockquote.di

import com.kurmakaeva.anastasia.lockquote.repository.GeniusRepo
import com.kurmakaeva.anastasia.lockquote.domain.InterfaceGeniusRepo
import com.kurmakaeva.anastasia.lockquote.service.GeniusSearchService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Singleton
    @Provides
    fun provideRepository(service: GeniusSearchService): InterfaceGeniusRepo = GeniusRepo(service)
}
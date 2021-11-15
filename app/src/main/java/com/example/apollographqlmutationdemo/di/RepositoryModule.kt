package com.example.apollographqlmutationdemo.di

import com.example.apollographqlmutationdemo.data.network.CharApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

// Singleton ..here we will provide all 3rd party library Obj..
@InstallIn(SingletonComponent::class)
@Module
object RepositoryModule {

    @Singleton
    @Provides
    fun provideNetworkServices(): CharApiService {
        return CharApiService()
    }
}

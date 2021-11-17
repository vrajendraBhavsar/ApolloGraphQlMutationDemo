package com.example.apollographqlmutationdemo.di

import com.example.apollographqlmutationdemo.data.login.repository.LoginRepositoryImpl
import com.example.apollographqlmutationdemo.data.tripAction.repository.TripActionRepositoryImpl
import com.example.apollographqlmutationdemo.domain.login.LoginRepository
import com.example.apollographqlmutationdemo.domain.tripAction.TripActionRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {
    @Binds
    @ViewModelScoped
    abstract fun bindLoginRepository(loginRepositoryImpl: LoginRepositoryImpl): LoginRepository

    @Binds
    @ViewModelScoped
    abstract fun bindTripActionRepository(tripActionRepositoryImpl: TripActionRepositoryImpl): TripActionRepository
}
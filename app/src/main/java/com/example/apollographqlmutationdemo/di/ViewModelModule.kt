package com.example.apollographqlmutationdemo.di

import com.example.apollographqlmutationdemo.data.login.repository.LoginRepositoryImpl
import com.example.apollographqlmutationdemo.domain.login.LoginRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class ViewModelModule {
    @Binds
    @ViewModelScoped
    abstract fun bindSingleCharacterRepository(singleCharRepositoryImpl: LoginRepositoryImpl): LoginRepository
}
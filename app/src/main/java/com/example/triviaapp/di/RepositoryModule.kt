package com.example.triviaapp.di

import com.example.triviaapp.data.UserDao
import com.example.triviaapp.repository.UserRepository
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
    fun provideRepository( dao : UserDao ) : UserRepository{
        return UserRepository( dao )
    }
}
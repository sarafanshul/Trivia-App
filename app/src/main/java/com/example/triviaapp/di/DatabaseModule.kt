package com.example.triviaapp.di

import android.app.Application
import com.example.triviaapp.data.UserDao
import com.example.triviaapp.data.UserDatabase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun provideDao( application: Application ) : UserDao {
        return UserDatabase.getInstance(application).userDao()
    }
}
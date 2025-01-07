package com.example.myapplication2.hilt

import com.example.myapplication2.api.APIService
import com.example.myapplication2.api.RetrofitInterface
import com.example.myapplication2.api.Repository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class) // Durée de vie de l'application
object AppModule {

    @Provides
    fun provideApiService(): APIService = RetrofitInterface.api

    @Provides
    fun provideRepository(api: APIService): Repository = Repository(api)
}
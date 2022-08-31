package com.ibnu.validquote.di

import com.ibnu.validquote.data.remote.service.QuoteService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit

@Module
@InstallIn(SingletonComponent::class)
class ServiceModule {

    @Provides
    fun provideQuoteService(retrofit: Retrofit): QuoteService {
        return retrofit.create(QuoteService::class.java)
    }

}
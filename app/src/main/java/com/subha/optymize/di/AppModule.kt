package com.subha.optymize.di

import com.subha.optymize.core.util.Constants
import com.subha.optymize.data.remote.api.ShoeaApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class AppModule {


    @Singleton
    @Provides
    fun providesRetrofit(): Retrofit.Builder {
        return Retrofit.Builder().baseUrl(Constants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }


    @Singleton
    @Provides
    fun providesShoeaAPI(retrofitBuilder: Retrofit.Builder): ShoeaApi {
        return retrofitBuilder.build().create(ShoeaApi::class.java)
    }
}
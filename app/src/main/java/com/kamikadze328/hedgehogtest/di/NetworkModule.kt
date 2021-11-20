package com.kamikadze328.hedgehogtest.di

import com.kamikadze328.hedgehogtest.data.Webservice
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent


@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Provides
    fun provideWebservice(): Webservice = Webservice.create()
}

package com.kamikadze328.hedgehogtest.di

import com.kamikadze328.hedgehogtest.data.utils.NumbersInputFilter
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
class UtilsModule {
    @Singleton
    @Provides
    fun provideNumberInputFilter(): NumbersInputFilter = NumbersInputFilter()
}

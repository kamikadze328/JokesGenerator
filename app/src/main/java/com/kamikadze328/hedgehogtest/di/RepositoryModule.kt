package com.kamikadze328.hedgehogtest.di

import android.content.Context
import com.kamikadze328.hedgehogtest.data.Webservice
import com.kamikadze328.hedgehogtest.data.repository.JokesRepository
import com.kamikadze328.hedgehogtest.data.repository.JokesRepositoryImpl
import com.kamikadze328.hedgehogtest.data.repository.MessageRepository
import com.kamikadze328.hedgehogtest.data.repository.MessageRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Inject


@Module
@InstallIn(SingletonComponent::class)
class RepositoryModule {

    @Provides
    @Inject
    fun provideJokesRepository(
        webservice: Webservice,
        messageRepository: MessageRepository,
        @ApplicationContext context: Context
    ): JokesRepository = JokesRepositoryImpl(webservice, messageRepository, context)

    @Provides
    @Inject
    fun provideMessageRepository(@ApplicationContext context: Context): MessageRepository =
        MessageRepositoryImpl(context)
}

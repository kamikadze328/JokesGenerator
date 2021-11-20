package com.kamikadze328.hedgehogtest.data.repository

import android.content.Context
import com.kamikadze328.hedgehogtest.R
import javax.inject.Inject

interface MessageRepository {
    fun youAreOffline(): String
    fun noJokes(): String
}

class MessageRepositoryImpl @Inject constructor(
    private val context: Context
) : MessageRepository {
    override fun youAreOffline(): String = context.getString(R.string.you_are_offline)
    override fun noJokes(): String = context.getString(R.string.no_jokes)
}


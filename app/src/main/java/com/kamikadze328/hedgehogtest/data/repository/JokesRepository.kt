package com.kamikadze328.hedgehogtest.data.repository

import android.content.Context
import com.kamikadze328.hedgehogtest.data.Webservice
import com.kamikadze328.hedgehogtest.data.dto.JokesResponse
import com.kamikadze328.hedgehogtest.data.utils.isInternetAvailable
import io.reactivex.Observable
import java.math.BigInteger
import java.net.UnknownHostException
import javax.inject.Inject

interface JokesRepository {
    fun loadJokes(count: BigInteger = BigInteger.ONE): Observable<JokesResponse>
}

class JokesRepositoryImpl @Inject constructor(
    private val webservice: Webservice,
    private val messageRepository: MessageRepository,
    private val context: Context
) : JokesRepository {

    override fun loadJokes(count: BigInteger): Observable<JokesResponse> {
        if (!isInternetAvailable(context)) throw UnknownHostException(messageRepository.youAreOffline())
        if (count < BigInteger.ONE) throw IllegalStateException(messageRepository.noJokes())
        val countStr = if (count < BigInteger.ONE) "1" else count.toString()
        return webservice.loadRandom(countStr)
            .doOnEach {
                if (it.isOnNext && it.value?.type != "success") throw Throwable(it.value?.type)
            }
    }
}

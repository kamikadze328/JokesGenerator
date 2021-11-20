package com.kamikadze328.hedgehogtest.data

import com.kamikadze328.hedgehogtest.data.dto.JokesResponse
import io.reactivex.Observable
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Path
import java.util.concurrent.TimeUnit

interface Webservice {
    companion object {
        private const val BASE_URL = "https://api.icndb.com/jokes/"
        private const val TIMEOUT_DEFAULT = 10L
        private const val TIMEOUT_CONNECT = TIMEOUT_DEFAULT
        private const val TIMEOUT_READ = TIMEOUT_DEFAULT
        private const val TIMEOUT_WRITE = TIMEOUT_DEFAULT

        fun create(): Webservice {
            return Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(createHTTPClient())
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(Webservice::class.java)
        }

        private fun createHTTPClient() = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor())
            .connectTimeout(TIMEOUT_CONNECT, TimeUnit.SECONDS)
            .readTimeout(TIMEOUT_READ, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT_WRITE, TimeUnit.SECONDS)
            .build()
    }

    @GET("random/{jokes_count}")
    fun loadRandom(@Path(value = "jokes_count") jokesCount: String): Observable<JokesResponse>
}

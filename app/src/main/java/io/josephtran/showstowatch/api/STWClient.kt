package io.josephtran.showstowatch.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Observable
import java.util.concurrent.TimeUnit

class STWClient {
    private val stwService: STWService
    private val stwUrl = "http://showstowatch.josephtran.io/"

    init {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build();
        val retrofit = Retrofit.Builder()
                .baseUrl(stwUrl)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        stwService = retrofit.create(STWService::class.java)
    }

    private fun getSTWShows(): List<STWShow> {
        val shows = stwService.listShows()
        val response = shows.execute()
        if (response.isSuccessful) return response.body()
        return emptyList()
    }

    fun getShows(): Observable<List<STWShow>> {
        return Observable.defer { Observable.just(getSTWShows()) }
    }
}
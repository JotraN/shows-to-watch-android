package io.josephtran.showstowatch.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import rx.Observable
import java.util.concurrent.TimeUnit

class STWClient {
    private val stwService: STWService

    init {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(STW_BASE_URL)
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

    fun getLoginUrl() = "${STW_BASE_URL}users/sign_in"

    fun getRedirectUrl() = "${STW_BASE_URL}users/request_token.json"
}
package io.josephtran.showstowatch.api

import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit

class STWClient(val user: String, val token: String) {
    private val stwService: STWService

    init {
        val httpClient = OkHttpClient().newBuilder()
                .connectTimeout(60, TimeUnit.SECONDS)
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .build()
        val retrofit = Retrofit.Builder()
                .baseUrl(STW_BASE_API_URL)
                .client(httpClient)
                .addConverterFactory(MoshiConverterFactory.create())
                .build()
        stwService = retrofit.create(STWService::class.java)
    }

    fun getLoginUrl() = "${STW_BASE_URL}users/sign_in"

    fun getRedirectUrl() = "${STW_BASE_API_URL}users/request_token.json"

    fun getShows(): List<STWShow> {
        val response = stwService.listShows().execute()
        if (response.isSuccessful) return response.body()
        return emptyList()
    }

    fun getInProgressShows(): List<STWShow> {
        val response = stwService.listInProgressShows().execute()
        if (response.isSuccessful) return response.body()
        return emptyList()
    }

    fun getAbandonedShows(): List<STWShow> {
        val response = stwService.listAbandonedShows().execute()
        if (response.isSuccessful) return response.body()
        return emptyList()
    }

    fun getCompletedShows(): List<STWShow> {
        val response = stwService.listCompletedShows().execute()
        if (response.isSuccessful) return response.body()
        return emptyList()
    }

    fun addShow(show: STWShow): STWShow? {
        val response = stwService.addShow(user, token, show).execute()
        if (response.isSuccessful) return response.body()
        return null
    }

    fun deleteShow(show: STWShow): Boolean {
        val response = stwService.deleteShow(user, token, show.id!!).execute()
        return response.isSuccessful && response.body().has("success")
    }

    fun editShow(show: STWShow): STWShow? {
        val response = stwService.editShow(user, token, show.id!!, show).execute()
        if (response.isSuccessful) return response.body()
        return null
    }

    fun searchTVDBShow(show: STWShow): List<TVDBShow> {
        val response = stwService.searchTVDBShow(user, token, show.id!!).execute()
        if (response.isSuccessful) return response.body()
        return emptyList()
    }
}
package io.josephtran.showstowatch.api

import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

public class STWClient {
    private val stwService: STWService;
    private val stwUrl = "http://showstowatch.josephtran.io/"

    init {
        val retrofit = Retrofit.Builder()
                .addConverterFactory(MoshiConverterFactory.create())
                .baseUrl(stwUrl)
                .build()
        stwService = retrofit.create(STWService::class.java)
    }

    public fun getShows(): List<STWShow> {
        val shows = stwService.listShows()
        val response = shows.execute()
        if (response.isSuccessful) return response.body()
        return emptyList()
    }
}
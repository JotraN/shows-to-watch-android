package io.josephtran.showstowatch.api

import retrofit2.Call
import retrofit2.http.GET

public interface STWService {
    @GET("shows.json")
    fun listShows(): Call<List<STWShow>>
}


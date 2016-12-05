package io.josephtran.showstowatch.api

import retrofit2.Call
import retrofit2.http.GET

val TVDB_IMG_URL = "http://thetvdb.com/banners/"

interface STWService {
    @GET("shows.json")
    fun listShows(): Call<List<STWShow>>
}


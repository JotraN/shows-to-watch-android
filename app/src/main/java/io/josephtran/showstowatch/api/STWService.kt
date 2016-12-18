package io.josephtran.showstowatch.api

import org.json.JSONObject
import retrofit2.Call
import retrofit2.http.*

val STW_BASE_URL = "http://showstowatch.josephtran.io/api/"
val TVDB_IMG_URL = "http://thetvdb.com/banners/"

interface STWService {
    @GET("shows.json")
    fun listShows(): Call<List<STWShow>>

    @POST("shows.json")
    fun addShow(@Header("X-User-Email") email: String,
                @Header("X-User-Token") token: String,
                @Body show: STWShow): Call<STWShow>

    @DELETE("shows/{showId}.json")
    fun deleteShow(@Header("X-User-Email") email: String,
                   @Header("X-User-Token") token: String,
                   @Path("showId") id: Int): Call<JSONObject>

    @PATCH("shows/{showId}.json")
    fun editShow(@Header("X-User-Email") email: String,
                 @Header("X-User-Token") token: String,
                 @Path("showId") id: Int,
                 @Body show: STWShow): Call<STWShow>

    @GET("shows/{showId}/search.json")
    fun searchTVDBShow(@Header("X-User-Email") email: String,
                       @Header("X-User-Token") token: String,
                       @Path("showId") id: Int): Call<List<TVDBShow>>
}
package io.josephtran.showstowatch.api

import com.squareup.moshi.Json

data class STWShow(
        var id: Int?,
        @Json(name = "name")
        val title: String,
        val season: Int = 0,
        val episode: Int = 0,
        @Json(name = "created_at")
        val createdAt: String = "",
        @Json(name = "updated_at")
        val updatedAt: String = "",
        val banner: String = "",
        val tvdbId: String = ""
)
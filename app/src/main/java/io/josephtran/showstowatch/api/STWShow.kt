package io.josephtran.showstowatch.api

import com.squareup.moshi.Json

data class STWShow(
        @Json(name = "name")
        val title: String,
        val season: Int,
        val episode: Int,
        @Json(name = "created_at")
        val createdAt: String,
        @Json(name = "updated_at")
        val updatedAt: String
)
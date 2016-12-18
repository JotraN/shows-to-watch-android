package io.josephtran.showstowatch.api

import com.squareup.moshi.Json

data class TVDBShow(
        val id: Int,
        @Json(name = "seriesName")
        val title: String,
        val banner: String
)
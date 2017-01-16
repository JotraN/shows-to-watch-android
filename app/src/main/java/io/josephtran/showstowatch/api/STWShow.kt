package io.josephtran.showstowatch.api

import android.os.Parcel
import android.os.Parcelable
import com.squareup.moshi.Json

data class STWShow(
        var id: Int?,
        @Json(name = "name")
        var title: String,
        val season: Int = 0,
        val episode: Int = 0,
        @Json(name = "created_at")
        val createdAt: String = "",
        @Json(name = "updated_at")
        val updatedAt: String = "",
        var banner: String = "",
        @Json(name = "tvdb_id")
        var tvdbId: String = "",
        val abandoned: Boolean = false,
        val completed: Boolean = false
) : Parcelable {
    companion object {
        @JvmField val CREATOR: Parcelable.Creator<STWShow> = object : Parcelable.Creator<STWShow> {
            override fun createFromParcel(source: Parcel): STWShow = STWShow(source)
            override fun newArray(size: Int): Array<STWShow?> = arrayOfNulls(size)
        }
    }

    constructor(source: Parcel) : this(
            source.readInt(), source.readString(), source.readInt(), source.readInt(),
            source.readString(), source.readString(), source.readString(), source.readString(),
            1 == source.readInt(), 1 == source.readInt())

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel?, flags: Int) {
        dest?.writeInt(id!!)
        dest?.writeString(title)
        dest?.writeInt(season)
        dest?.writeInt(episode)
        dest?.writeString(createdAt)
        dest?.writeString(updatedAt)
        dest?.writeString(banner)
        dest?.writeString(tvdbId)
        dest?.writeInt((if (abandoned) 1 else 0))
        dest?.writeInt((if (completed) 1 else 0))
    }
}
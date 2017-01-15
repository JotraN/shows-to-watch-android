package io.josephtran.showstowatch.show_search

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.TVDBShow
import io.josephtran.showstowatch.api.TVDB_IMG_URL
import java.util.*


class ShowSearchAdapter(val context: Context, val listener: ShowSearchAdapterListener)
    : RecyclerView.Adapter<ShowSearchAdapter.ViewHolder>() {
    private val tvdbShows = ArrayList<TVDBShow>()

    interface ShowSearchAdapterListener {
        fun onClick(tvdbShow: TVDBShow)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleTv =
                itemView!!.findViewById(R.id.stw_title_text) as TextView
        val bannerIv =
                itemView!!.findViewById(R.id.stw_banner_image) as ImageView

        init {
            itemView!!.setOnClickListener { listener.onClick(tvdbShows[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ShowSearchAdapter.ViewHolder {
        val v = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.show_item, parent, false)
        return ViewHolder(v)
    }

    override fun getItemCount(): Int {
        return tvdbShows.size
    }

    override fun onBindViewHolder(holder: ShowSearchAdapter.ViewHolder?, position: Int) {
        if (holder != null) {
            val show = tvdbShows[position]
            holder.titleTv.text = show.title
            Picasso.with(context)
                    .load(TVDB_IMG_URL + show.banner)
                    .into(holder.bannerIv)
        }
    }

    fun addAll(tvdbShows: List<TVDBShow>) {
        this.tvdbShows.addAll(tvdbShows)
        notifyDataSetChanged()
    }
}

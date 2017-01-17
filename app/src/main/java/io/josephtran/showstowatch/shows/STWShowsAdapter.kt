package io.josephtran.showstowatch.shows

import android.content.Context
import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.squareup.picasso.Picasso
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.api.TVDB_IMG_URL
import java.util.*


class STWShowsAdapter(val context: Context, val listener: ShowsAdapterListener)
    : RecyclerView.Adapter<STWShowsAdapter.ViewHolder>() {
    private val shows = ArrayList<STWShow>()

    interface ShowsAdapterListener {
        fun onClick(show: STWShow)
    }

    inner class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val overlay = itemView!!.findViewById(R.id.stw_overlay)
        val titleTv =
                itemView!!.findViewById(R.id.stw_title_text) as TextView
        val bannerIv =
                itemView!!.findViewById(R.id.stw_banner_image) as ImageView

        init {
            itemView!!.setOnClickListener { listener.onClick(shows[adapterPosition]) }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.show_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder != null) {
            val show = shows.get(position)
            holder.overlay.setBackgroundColor(getColor(show))
            holder.titleTv.text = show.title
            Picasso.with(context)
                    .load(TVDB_IMG_URL + show.banner)
                    .into(holder.bannerIv);
        }
    }

    private fun getColor(show: STWShow): Int {
        if (show.completed)
            return context.resources.getColor(R.color.darkGreen)
        if (show.abandoned)
            return context.resources.getColor(R.color.darkRed)
        return Color.BLACK
    }

    override fun getItemCount(): Int = shows.size

    fun addShows(shows: List<STWShow>) {
        this.shows.addAll(shows)
        notifyDataSetChanged()
    }

    fun clear() = shows.clear()
}

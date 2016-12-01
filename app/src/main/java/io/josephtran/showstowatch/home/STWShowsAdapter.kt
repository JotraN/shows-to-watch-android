package io.josephtran.showstowatch.home

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import java.util.*


class STWShowsAdapter : RecyclerView.Adapter<STWShowsAdapter.ViewHolder>() {
    val shows = ArrayList<STWShow>()

    class ViewHolder(itemView: View?) : RecyclerView.ViewHolder(itemView) {
        val titleTv: TextView =
                itemView!!.findViewById(R.id.stw_title_text) as TextView
        val seasonTv: TextView =
                itemView!!.findViewById(R.id.stw_season_text) as TextView
        val episodeTv: TextView =
                itemView!!.findViewById(R.id.stw_episode_text) as TextView
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): ViewHolder {
        val v = LayoutInflater.from(parent!!.context)
                .inflate(R.layout.stw_show_item, parent, false)
        return ViewHolder(v)
    }

    override fun onBindViewHolder(holder: ViewHolder?, position: Int) {
        if (holder != null) {
            val show = shows.get(position)
            holder.titleTv.text = show.title
            holder.seasonTv.text = show.season.toString()
            holder.episodeTv.text = show.episode.toString()
        }
    }

    override fun getItemCount(): Int = shows.size

    fun addShow(show: STWShow) {
        shows.add(show)
        notifyDataSetChanged()
    }
}

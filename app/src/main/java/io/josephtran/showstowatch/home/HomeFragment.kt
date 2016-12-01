package io.josephtran.showstowatch.home

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.josephtran.showstowatch.R
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment() {
    var adapter: STWShowsAdapter = STWShowsAdapter()

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        recycler_shows.layoutManager = LinearLayoutManager(context)
        recycler_shows.setHasFixedSize(true)
        recycler_shows.adapter = adapter
    }
}
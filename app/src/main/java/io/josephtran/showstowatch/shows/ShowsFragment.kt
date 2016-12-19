package io.josephtran.showstowatch.shows

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.GridLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import kotlinx.android.synthetic.main.fragment_shows.*

class ShowsFragment : Fragment(), HomeView {
    val adapter by lazy { STWShowsAdapter(context) }
    val progressView by lazy { home_progress }

    companion object {
        fun newInstance() = ShowsFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_shows, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_recycler.layoutManager = GridLayoutManager(context, 3)
        home_recycler.setHasFixedSize(true)
        home_recycler.adapter = adapter

        val presenter = ShowsPresenter(context, this)
        presenter.downloadShows()
    }

    override fun addShows(shows: List<STWShow>) {
        adapter.addShows(shows)
    }

    override fun showError(error: String) {
        if (view != null)
            Snackbar.make(view as View, error, Snackbar.LENGTH_SHORT).show()
    }

    override fun showProgress(show: Boolean) {
        progressView.visibility = if (show) View.VISIBLE else View.GONE
    }
}
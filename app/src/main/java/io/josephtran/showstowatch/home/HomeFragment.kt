package io.josephtran.showstowatch.home

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(), HomeView {
    val adapter by lazy { STWShowsAdapter(context) }
    val progressView by lazy { home_progress }

    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_home, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_recycler.layoutManager = LinearLayoutManager(context)
        home_recycler.setHasFixedSize(true)
        home_recycler.adapter = adapter

        val divider = DividerItemDecoration(context, LinearLayoutManager.VERTICAL)
        divider.setDrawable(ContextCompat.getDrawable(context, R.drawable.stw_show_item_divider))
        home_recycler.addItemDecoration(divider)

        val presenter = HomePresenter(this)
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
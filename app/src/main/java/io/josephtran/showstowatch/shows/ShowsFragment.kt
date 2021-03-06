package io.josephtran.showstowatch.shows

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import kotlinx.android.synthetic.main.fragment_shows.*


class ShowsFragment : Fragment(), ShowsView {
    private val adapter by lazy {
        STWShowsAdapter(context,
                object : STWShowsAdapter.ShowsAdapterListener {
                    override fun onClick(show: STWShow) {
                        if (listener != null) listener!!.onShowClicked(show)
                    }
                })
    }
    private val progressView by lazy { home_progress }
    private var listener: OnShowClickedListener? = null

    interface OnShowClickedListener {
        fun onShowClicked(show: STWShow)

        fun onShowsScrolled(dx: Int, dy: Int)
    }

    companion object {
        val SHOWS_TYPE = "SHOWS TYPE"

        fun newInstance(typeIndex: Int): ShowsFragment {
            val fragment = ShowsFragment()
            val args = Bundle()
            args.putInt(SHOWS_TYPE, typeIndex)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_shows, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        home_recycler.layoutManager = LinearLayoutManager(context)
        home_recycler.setHasFixedSize(true)
        home_recycler.adapter = adapter
        home_recycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (listener != null) listener!!.onShowsScrolled(dx, dy)
            }
        })

        val presenter = ShowsPresenter(context, this)
        val typeIndex = arguments.getInt(SHOWS_TYPE, 0)
        if (adapter.itemCount > 0)
            adapter.clear()
        presenter.downloadShows(typeIndex)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = activity as OnShowClickedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString()
                    + " must implement OnShowClickedListener")
        }
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
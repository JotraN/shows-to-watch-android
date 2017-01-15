package io.josephtran.showstowatch.show_search

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.api.TVDBShow
import kotlinx.android.synthetic.main.fragment_show_search.*


class ShowSearchFragment : Fragment(), ShowSearchView {

    private var listener: OnShowSearchedLstener? = null
    private val presenter by lazy { ShowSearchPresenter(context, this) }
    private val show by lazy { arguments.getParcelable<STWShow>(SHOW_SEARCH) }

    interface OnShowSearchedLstener {
        fun onSearched()
    }

    companion object {
        private val SHOW_SEARCH = "SHOW_SEARCH"

        fun newInstance(stwShow: STWShow): ShowSearchFragment {
            val fragment = ShowSearchFragment()
            val args = Bundle()
            args.putParcelable(SHOW_SEARCH, stwShow)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_show_search, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        show_search_recycler.layoutManager = LinearLayoutManager(context)
        show_search_recycler.setHasFixedSize(true)
        presenter.searchShow(show!!)
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = activity as OnShowSearchedLstener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString() + " must implement OnShowSearchedListener")
        }
    }

    override fun showSearchResults(tvdbShows: List<TVDBShow>) {
        val adapter = ShowSearchAdapter(context,
                object : ShowSearchAdapter.ShowSearchAdapterListener {
                    override fun onClick(tvdbShow: TVDBShow) {
                        presenter.setTvdbShow(show!!, tvdbShow)
                    }
                })
        adapter.addAll(tvdbShows)
        show_search_recycler.adapter = adapter
    }

    override fun showProgress(show: Boolean) {
        search_progress.visibility = if (show) View.VISIBLE else View.GONE
    }

    override fun onTVDBSet() {
        if (listener != null)
            listener!!.onSearched()
    }
}

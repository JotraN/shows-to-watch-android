package io.josephtran.showstowatch.show_search

import android.content.Context
import io.josephtran.showstowatch.api.STWClientWrapper
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.api.TVDBShow
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ShowSearchPresenter(context: Context, val view: ShowSearchView) {
    private val stwClient = STWClientWrapper(context)

    fun searchShow(show: STWShow) {
        view.showProgress(true)
        stwClient.searchTVDBShow(show)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    tvdbShows ->
                    view.showProgress(false)
                    view.showSearchResults(tvdbShows)
                }, { e -> view.onTVDBSet() })
    }

    fun setTVDBShow(show: STWShow, tvdbShow: TVDBShow) {
        view.showProgress(true)
        show.title = tvdbShow.title
        show.tvdbId = tvdbShow.id.toString()
        show.banner = tvdbShow.banner
        stwClient.editShow(show)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe { view.onTVDBSet() }
    }
}
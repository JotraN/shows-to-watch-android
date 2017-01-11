package io.josephtran.showstowatch.shows

import android.content.Context
import android.util.Log
import io.josephtran.showstowatch.api.STWClientWrapper
import io.josephtran.showstowatch.api.STWShow
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ShowsPresenter(context: Context, val view: ShowsView) {
    private val client = STWClientWrapper(context)

    companion object {
        val ALL_SHOWS = "All"
        val IN_PROGRESS_SHOWS = "In Progress"
        val ABANDONED_SHOWS = "Abandoned"
        val COMPLETED_SHOWS = "Completed"
        val SHOWS_TYPES = arrayOf(ALL_SHOWS, IN_PROGRESS_SHOWS, ABANDONED_SHOWS, COMPLETED_SHOWS)

        fun getShowsType(typeIndex: Int): String {
            if (typeIndex > SHOWS_TYPES.size || typeIndex < 0)
                return ""
            return SHOWS_TYPES.get(typeIndex)
        }

        fun getShowsTypesNum(): Int = SHOWS_TYPES.size

        fun getTypeIndex(showsType: String) = SHOWS_TYPES.indexOf(showsType)
    }

    fun downloadShows(index: Int) {
        val showsType = getShowsType(index)
        when (showsType) {
            ALL_SHOWS -> downloadAllShows()
            IN_PROGRESS_SHOWS -> downloadInProgressShows()
            ABANDONED_SHOWS -> downloadAbandonedShows()
            COMPLETED_SHOWS -> downloadCompletedShows()
            else -> downloadAllShows()
        }
    }

    private fun downloadAllShows() {
        view.showProgress(true)
        client.getShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(STWShowsSubscriber(view))
    }

    private fun downloadInProgressShows() {
        view.showProgress(true)
        client.getInProgressShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(STWShowsSubscriber(view))
    }

    private fun downloadAbandonedShows() {
        view.showProgress(true)
        client.getAbandonedShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(STWShowsSubscriber(view))
    }

    private fun downloadCompletedShows() {
        view.showProgress(true)
        client.getCompletedShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(STWShowsSubscriber(view))
    }

    class STWShowsSubscriber(val view: ShowsView) : Subscriber<List<STWShow>>() {
        private val errorMessage = "Error downloading shows."

        override fun onNext(shows: List<STWShow>?) {
            if (shows == null) view.showError(errorMessage)
            else view.addShows(shows.sortedByDescending(STWShow::updatedAt))
        }

        override fun onCompleted() {
            view.showProgress(false)
        }

        override fun onError(e: Throwable?) {
            view.showProgress(false)
            view.showError(errorMessage)
            Log.e(javaClass.name, errorMessage, e)
        }
    }
}
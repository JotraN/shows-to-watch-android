package io.josephtran.showstowatch.home

import android.content.Context
import android.util.Log
import io.josephtran.showstowatch.api.STWClientWrapper
import io.josephtran.showstowatch.api.STWShow
import rx.Subscriber
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class HomePresenter(val context: Context, val view: HomeView) {
    val client = STWClientWrapper(context)

    fun downloadShows() {
        view.showProgress(true)
        client.getShows()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(object : Subscriber<List<STWShow>>() {
                    private val errorMessage = "Error downloading shows."

                    override fun onNext(shows: List<STWShow>?) {
                        if (shows == null) view.showError(errorMessage)
                        else view.addShows(shows)
                    }

                    override fun onCompleted() {
                        view.showProgress(false)
                    }

                    override fun onError(e: Throwable?) {
                        view.showProgress(false)
                        view.showError(errorMessage)
                        Log.e(javaClass.name, errorMessage, e)
                    }

                })
    }
}

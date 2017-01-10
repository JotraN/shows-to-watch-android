package io.josephtran.showstowatch.show_add

import android.content.Context
import io.josephtran.showstowatch.api.STWClientWrapper
import io.josephtran.showstowatch.api.STWShow
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ShowAddPresenter(context: Context, val view: ShowFormView) {
    private val stwClient = STWClientWrapper(context)

    fun addShow(show: STWShow) {
        stwClient.addShow(show)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe({
                    s ->
                    if (s == null)
                        view.showMessage("Failed")
                    else
                        view.showMessage("Added show")
                })
    }
}
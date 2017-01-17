package io.josephtran.showstowatch.show_edit

import android.content.Context
import io.josephtran.showstowatch.api.STWClientWrapper
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.show_form.ShowFormView
import rx.android.schedulers.AndroidSchedulers
import rx.schedulers.Schedulers

class ShowEditPresenter(context: Context, val view: ShowFormView) {
    private val stwClient = STWClientWrapper(context)

    fun editShow(show: STWShow) {
        stwClient.editShow(show)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { s ->
                            if (s == null)
                                view.showErrorMessage("Failed to edit show.")
                            else
                                view.onShowHandled(s)
                        }, { e -> view.showErrorMessage(e.message!!) }
                )
    }

    fun deleteShow(show: STWShow) {
        stwClient.deleteShow(show)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { s ->
                            if (s)
                                view.onShowHandled(show)
                            else
                                view.showErrorMessage("Failed to delete show.")
                        }, { e -> view.showErrorMessage(e.message!!) }
                )
    }
}

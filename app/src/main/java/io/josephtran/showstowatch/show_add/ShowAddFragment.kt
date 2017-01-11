package io.josephtran.showstowatch.show_add

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import kotlinx.android.synthetic.main.activity_show_form.*
import kotlinx.android.synthetic.main.fragment_show_form.*

val SHOW_ADD_SUCCESS_CODE = 4

class ShowAddFragment : Fragment(), ShowFormView {

    companion object {
        fun newInstance() = ShowAddFragment()
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        val view = inflater!!.inflate(R.layout.fragment_show_form, container, false)
        activity.show_form_toolbar_btn.setText(R.string.show_form_action_add)
        activity.show_form_toolbar_btn.setOnClickListener {
            val show = constructShow()
            if (show != null) {
                val presenter = ShowAddPresenter(context, this)
                presenter.addShow(show)
            }
        }
        return view
    }

    private fun constructShow(): STWShow? {
        if (!fieldsValid())
            return null
        return STWShow(
                id = null,
                title = show_form_title_edit.text.toString(),
                season = show_form_season_edit.text.toString().toInt(),
                episode = show_form_episode_edit.text.toString().toInt(),
                abandoned = show_form_abandoned.isChecked,
                completed = show_form_completed.isChecked
        )
    }

    private fun fieldsValid(): Boolean {
        var message = ""
        if (show_form_title_edit.text.isNullOrEmpty()) {
            message = "Title required."
        } else if (show_form_season_edit.text.isNullOrEmpty()) {
            message = "Season required."
        } else if (show_form_episode_edit.text.isNullOrEmpty()) {
            message = "Episode required."
        }
        if (!message.isNullOrEmpty()) {
            showErrorMessage(message)
            return false
        }
        return true
    }

    private fun showMessage(message: String, color: String) {
        if (view != null)
            Snackbar.make(view!!,
                    Html.fromHtml("<font color=\"$color\">$message</font>"),
                    Snackbar.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        showMessage(message, "white")
    }

    override fun showErrorMessage(errorMessage: String) {
        showMessage(errorMessage, "red")
    }

    override fun closeView() {
        activity.setResult(SHOW_ADD_SUCCESS_CODE)
        activity.finish()
    }
}
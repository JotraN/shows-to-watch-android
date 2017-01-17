package io.josephtran.showstowatch.show_form

import android.content.Context
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

abstract class ShowFormFragment : Fragment(), ShowFormView {
    private var listener: ShowFormFragmentListener? = null

    interface ShowFormFragmentListener {
        fun onFormInteracted(show: STWShow)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View?
            = inflater!!.inflate(R.layout.fragment_show_form, container, false)

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity.show_form_toolbar_btn.text = getFormButtonText()
        activity.show_form_toolbar_btn.setOnClickListener {
            val show = constructShow()
            if (show != null)
                handleShow(show)
        }
    }

    /**
     * Gets the text used for the form button.
     *
     * @return the text used for the form button
     */
    abstract protected fun getFormButtonText(): String

    /**
     * Handle the {@code STWShow} constructed from the form.
     *
     * @param stwShow the constructed {@code STWShow} from the form
     */
    abstract protected fun handleShow(stwShow: STWShow)

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = activity as ShowFormFragmentListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString()
                    + " must implement ShowFormFragmentListener")
        }
    }

    /**
     * Populates the form with the given {@code STWShow}.
     *
     * @param stwShow the {@code STWShow} to populate the form with
     */
    protected fun populateForm(stwShow: STWShow) {
        show_form_title_edit.setText(stwShow.title)
        show_form_season_edit.setText(stwShow.season.toString())
        show_form_episode_edit.setText(stwShow.episode.toString())
        show_form_abandoned.isChecked = stwShow.abandoned
        show_form_completed.isChecked = stwShow.completed
    }

    protected open fun constructShow(): STWShow? {
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

    override fun onShowHandled(show: STWShow) {
        if (listener != null)
            listener!!.onFormInteracted(show)
    }
}
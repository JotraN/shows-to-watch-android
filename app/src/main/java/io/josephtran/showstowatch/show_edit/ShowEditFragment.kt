package io.josephtran.showstowatch.show_edit

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.Button
import android.widget.LinearLayout
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.show_form.ShowFormFragment
import kotlinx.android.synthetic.main.fragment_show_form.*

class ShowEditFragment : ShowFormFragment() {
    private val presenter by lazy { ShowEditPresenter(context, this) }
    private val show by lazy { arguments.getParcelable<STWShow>(SHOW_EDIT) }
    private var listener: OnTVDBButtonClickedListener? = null

    interface OnTVDBButtonClickedListener {
        fun onTVDBButtonClicked(show: STWShow)
    }

    companion object {
        val SHOW_EDIT = "SHOW_EDIT"

        fun newInstance(stwShow: STWShow): ShowEditFragment {
            val fragment = ShowEditFragment()
            val args = Bundle()
            args.putParcelable(SHOW_EDIT, stwShow)
            fragment.arguments = args
            return fragment
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        populateForm(show)
        addEditButtons()
    }

    private fun addEditButtons() {
        val layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                LinearLayout.LayoutParams.WRAP_CONTENT)
        val tvdbButton = Button(context)
        tvdbButton.text = getString(R.string.show_form_tvdb_button)
        tvdbButton.setOnClickListener { if (listener != null) listener!!.onTVDBButtonClicked(show) }
        show_form_layout.addView(tvdbButton, layoutParams)

        val deleteButton = Button(context)
        deleteButton.text = getString(R.string.show_form_delete_button)
        deleteButton.setOnClickListener { promptDeleteShow() }
        show_form_layout.addView(deleteButton, layoutParams)
    }

    private fun promptDeleteShow() {
        AlertDialog.Builder(context)
                .setMessage(R.string.show_form_delete_prompt)
                .setPositiveButton(R.string.show_form_positive_button) {
                    dialog, which ->
                    presenter.deleteShow(show)
                }
                .setNegativeButton(R.string.show_form_negative_button) {
                    dialog, which ->
                    dialog.cancel()
                }
                .show()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        try {
            listener = activity as OnTVDBButtonClickedListener?
        } catch (e: ClassCastException) {
            throw ClassCastException(activity.toString()
                    + " must implement OnTVDBButtonClickedListener")
        }
    }

    override fun getFormButtonText(): String {
        return getString(R.string.show_form_action_save)
    }

    override fun handleShow(stwShow: STWShow) {
        presenter.editShow(mergeShows(show, stwShow))
    }

    override fun constructShow(): STWShow? {
        val constructedShow = super.constructShow() ?: return null
        return mergeShows(show, constructedShow)
    }

    private fun mergeShows(original: STWShow, updated: STWShow): STWShow {
        return STWShow(
                title = updated.title,
                season = updated.season,
                episode = updated.episode,
                abandoned = updated.abandoned,
                completed = updated.completed,
                id = original.id,
                tvdbId = original.tvdbId,
                banner = original.banner
        )
    }
}

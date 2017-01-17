package io.josephtran.showstowatch.show_add

import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.show_form.ShowFormFragment

class ShowAddFragment : ShowFormFragment() {

    companion object {
        fun newInstance() = ShowAddFragment()
    }

    override fun getFormButtonText(): String {
        return getString(R.string.show_form_action_add)
    }

    override fun handleShow(stwShow: STWShow) {
        val presenter = ShowAddPresenter(context, this)
        presenter.addShow(stwShow)
    }
}
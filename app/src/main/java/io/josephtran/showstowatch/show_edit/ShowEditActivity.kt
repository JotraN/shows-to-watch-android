package io.josephtran.showstowatch.show_edit

import android.app.Activity
import android.support.v4.app.Fragment
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.show_form.ShowFormActivity

val SHOW_EDIT_ID = "SHOW_EDIT_ID"

class ShowEditActivity : ShowFormActivity(), ShowEditFragment.OnTVDBButtonClickedListener {

    override fun getFragment(): Fragment {
        val show = intent.getParcelableExtra<STWShow>(SHOW_EDIT_ID)
        return ShowEditFragment.newInstance(show)
    }

    override fun onFormInteracted(show: STWShow) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onTVDBButtonClicked(show: STWShow) {
        openSearch(show)
    }
}
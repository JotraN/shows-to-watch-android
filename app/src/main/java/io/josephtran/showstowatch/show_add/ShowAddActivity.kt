package io.josephtran.showstowatch.show_add

import android.support.v4.app.Fragment
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.show_form.ShowFormActivity

class ShowAddActivity : ShowFormActivity() {
    override fun getFragment(): Fragment {
        return ShowAddFragment.newInstance()
    }

    override fun onFormInteracted(show: STWShow) {
        openSearch(show)
    }
}
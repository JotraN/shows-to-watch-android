package io.josephtran.showstowatch.show_add

import android.support.v4.app.Fragment
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.show_form.ShowFormActivity

class ShowAddActivity : ShowFormActivity() {

    override fun getFragment(): Fragment {
        return ShowAddFragment.newInstance()
    }

    override fun loadToolbarImage(toolbarImage: ImageView) {
        Picasso.with(this)
                .load(R.drawable.show_add_toolbar_bg)
                .into(toolbarImage)
    }

    override fun getToolbarTitle(): String {
        return resources.getString(R.string.show_form_add_title)
    }

    override fun onFormInteracted(show: STWShow) {
        openSearch(show)
    }
}
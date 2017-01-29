package io.josephtran.showstowatch.show_edit

import android.app.Activity
import android.support.v4.app.Fragment
import android.widget.ImageView
import com.squareup.picasso.Picasso
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.api.TVDB_IMG_URL
import io.josephtran.showstowatch.show_form.ShowFormActivity

val SHOW_EDIT_ID = "SHOW_EDIT_ID"

class ShowEditActivity : ShowFormActivity(), ShowEditFragment.OnTVDBButtonClickedListener {
    val show: STWShow by lazy { intent.getParcelableExtra<STWShow>(SHOW_EDIT_ID) }

    override fun getFragment(): Fragment {
        return ShowEditFragment.newInstance(show)
    }

    override fun loadToolbarImage(toolbarImage: ImageView) {
        Picasso.with(this)
                .load(TVDB_IMG_URL + show.banner)
                .into(toolbarImage)
    }

    override fun getToolbarTitle(): String {
        return show.title
    }

    override fun onFormInteracted(show: STWShow) {
        setResult(Activity.RESULT_OK)
        finish()
    }

    override fun onTVDBButtonClicked(show: STWShow) {
        openSearch(show)
    }
}
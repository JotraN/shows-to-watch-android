package io.josephtran.showstowatch.show_form

import android.app.Activity
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.show_search.ShowSearchFragment
import kotlinx.android.synthetic.main.activity_show_form.*

abstract class ShowFormActivity : AppCompatActivity(),
        ShowFormFragment.ShowFormFragmentListener, ShowSearchFragment.OnShowSearchedLstener {

    private val SHOW_SEARCH_FRAGMENT_TAG = "SHOW_SEARCH_FRAGMENT_TAG"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_show_form)
        setSupportActionBar(show_form_toolbar)
        if (supportActionBar != null) {
            supportActionBar!!.setDisplayHomeAsUpEnabled(true)
            supportActionBar!!.setDisplayShowHomeEnabled(true)
        }
        if (savedInstanceState != null) return
        supportFragmentManager.beginTransaction()
                .add(R.id.show_form_fragment_container, getFragment()).commit()
    }

    abstract protected fun getFragment(): Fragment

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId === android.R.id.home) {
            onBackPressed()
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onBackPressed() {
        // Always succeed when returning from search.
        if (supportFragmentManager.findFragmentByTag(SHOW_SEARCH_FRAGMENT_TAG) != null)
            setResult(Activity.RESULT_OK)
        super.onBackPressed()
    }

    protected fun openSearch(show: STWShow) {
        show_form_toolbar.removeView(show_form_toolbar_btns_layout)
        show_form_toolbar.title = getString(R.string.show_search_title)
        supportFragmentManager.beginTransaction()
                .replace(R.id.show_form_fragment_container,
                        ShowSearchFragment.newInstance(show), SHOW_SEARCH_FRAGMENT_TAG)
                .commit()
    }

    override fun onSearched() {
        setResult(Activity.RESULT_OK)
        finish()
    }
}
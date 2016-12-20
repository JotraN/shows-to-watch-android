package io.josephtran.showstowatch.shows

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import io.josephtran.showstowatch.R
import kotlinx.android.synthetic.main.activity_shows.*

val PREF_STW_KEY = "PREF_STW_KEY"
val PREF_STW_USER_KEY = "PREF_STW_USER_KEY"
val PREF_STW_TOKEN_KEY = "PREF_STW_TOKEN_KEY"

class ShowsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shows)
        val pager = shows_pager
        pager.adapter = object : FragmentPagerAdapter(supportFragmentManager) {
            override fun getItem(position: Int): Fragment {
                return ShowsFragment.newInstance(position)
            }

            override fun getCount(): Int {
                return ShowsPresenter.getShowsTypesNum()
            }

            override fun getPageTitle(position: Int): CharSequence {
                return ShowsPresenter.getShowsType(position)
            }
        }
        pager.setCurrentItem(ShowsPresenter.getTypeIndex(ShowsPresenter.IN_PROGRESS_SHOWS), true)
        shows_tab_layout.setupWithViewPager(pager)
    }
}
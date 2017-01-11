package io.josephtran.showstowatch.shows

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter

class ShowsPagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {
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
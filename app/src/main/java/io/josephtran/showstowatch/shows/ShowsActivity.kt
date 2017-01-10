package io.josephtran.showstowatch.shows

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentPagerAdapter
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.login.LOGIN_SUCCESS_CODE
import io.josephtran.showstowatch.login.LoginActivity
import io.josephtran.showstowatch.show_add.ShowAddActivity
import kotlinx.android.synthetic.main.activity_shows.*

val PREF_STW_KEY = "PREF_STW_KEY"
val PREF_STW_USER_KEY = "PREF_STW_USER_KEY"
val PREF_STW_TOKEN_KEY = "PREF_STW_TOKEN_KEY"

class ShowsActivity : AppCompatActivity() {
    private val LOGIN_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shows)
        setSupportActionBar(shows_toolbar)
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

        shows_fab.setOnClickListener { startActivity(Intent(this, ShowAddActivity::class.java)) }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_shows, menu)
        initLoginMenuItem(menu)
        return true
    }

    private fun initLoginMenuItem(menu: Menu) {
        val prefs = getSharedPreferences(PREF_STW_KEY, Context.MODE_PRIVATE)
        val loggedIn = prefs.contains(PREF_STW_USER_KEY) && prefs.contains(PREF_STW_TOKEN_KEY)
        menu.findItem(R.id.action_login).isVisible = !loggedIn
        menu.findItem(R.id.action_logout).isVisible = loggedIn
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_login -> {
                startActivityForResult(Intent(this, LoginActivity::class.java), LOGIN_REQUEST_CODE)
                return true
            }
            R.id.action_logout -> {
                val editor = getSharedPreferences(PREF_STW_KEY, Context.MODE_PRIVATE).edit()
                editor.remove(PREF_STW_USER_KEY)
                editor.remove(PREF_STW_TOKEN_KEY)
                editor.apply()
                invalidateOptionsMenu()
                Snackbar.make(shows_pager, "User logged out.", Snackbar.LENGTH_SHORT).show()
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            LOGIN_REQUEST_CODE ->
                if (resultCode == LOGIN_SUCCESS_CODE) {
                    Snackbar.make(shows_pager, "User logged in.", Snackbar.LENGTH_SHORT).show()
                    invalidateOptionsMenu()
                }
        }
    }
}
package io.josephtran.showstowatch.shows

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.login.LOGIN_SUCCESS_CODE
import io.josephtran.showstowatch.login.LoginActivity
import io.josephtran.showstowatch.show_add.ShowAddActivity
import io.josephtran.showstowatch.show_edit.SHOW_EDIT_ID
import io.josephtran.showstowatch.show_edit.ShowEditActivity
import kotlinx.android.synthetic.main.activity_shows.*

val PREF_STW_KEY = "PREF_STW_KEY"
val PREF_STW_USER_KEY = "PREF_STW_USER_KEY"
val PREF_STW_TOKEN_KEY = "PREF_STW_TOKEN_KEY"

class ShowsActivity : AppCompatActivity(), ShowsFragment.OnShowClickedListener {
    private val LOGIN_REQUEST_CODE = 1
    private val SHOW_ADD_REQUEST_CODE = 2
    private val SHOW_EDIT_REQUEST_CODE = 3

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shows)
        setSupportActionBar(shows_toolbar)
        refreshShows()
        shows_tab_layout.setupWithViewPager(shows_pager)

        shows_fab.setOnClickListener {
            startActivityForResult(Intent(this, ShowAddActivity::class.java), SHOW_ADD_REQUEST_CODE)
        }
    }

    private fun refreshShows() {
        shows_pager.adapter = ShowsPagerAdapter(supportFragmentManager)
        shows_pager.setCurrentItem(ShowsPresenter.getTypeIndex(ShowsPresenter.IN_PROGRESS_SHOWS),
                true)
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
            SHOW_ADD_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    Snackbar.make(shows_pager, "Show added.", Snackbar.LENGTH_SHORT).show()
                }
                // Always refresh shows.
                refreshShows()
            }
            SHOW_EDIT_REQUEST_CODE -> {
                if (resultCode == Activity.RESULT_OK) {
                    Snackbar.make(shows_pager, "Show edited.", Snackbar.LENGTH_SHORT).show()
                }
                // Always refresh shows.
                refreshShows()
            }
        }
    }

    override fun onShowClicked(show: STWShow) {
        // Necessary since STW sets TVDB to null by default for new shows.
        show.tvdbId = show.tvdbId ?: ""
        val intent = Intent(this, ShowEditActivity::class.java)
        intent.putExtra(SHOW_EDIT_ID, show)
        startActivityForResult(intent, SHOW_EDIT_REQUEST_CODE)
    }
}
package io.josephtran.showstowatch

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.josephtran.showstowatch.login.LoginFragment
import io.josephtran.showstowatch.shows.ShowsFragment

val PREF_STW_KEY = "PREF_STW_KEY"
val PREF_STW_USER_KEY = "PREF_STW_USER_KEY"
val PREF_STW_TOKEN_KEY = "PREF_STW_TOKEN_KEY"

class MainActivity : AppCompatActivity(), LoginFragment.LoginFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        if (savedInstanceState != null) return
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, LoginFragment.newInstance()).commit()
    }

    override fun onLoggedIn() {
        supportFragmentManager.beginTransaction()
                .add(R.id.fragment_container, ShowsFragment.newInstance()).commit()
    }
}

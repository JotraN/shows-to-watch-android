package io.josephtran.showstowatch.login

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import io.josephtran.showstowatch.R
import io.josephtran.showstowatch.shows.ShowsActivity


class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState != null) return
        supportFragmentManager.beginTransaction()
                .add(R.id.login_fragment_container, LoginFragment.newInstance()).commit()
    }

    override fun onLoggedIn() {
        val intent = Intent(this, ShowsActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        startActivity(intent)
        finish()
    }
}

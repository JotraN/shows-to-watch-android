package io.josephtran.showstowatch.login

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import io.josephtran.showstowatch.R

val LOGIN_SUCCESS_CODE = 2

class LoginActivity : AppCompatActivity(), LoginFragment.LoginFragmentListener {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        if (savedInstanceState != null) return
        supportFragmentManager.beginTransaction()
                .add(R.id.login_fragment_container, LoginFragment.newInstance()).commit()
    }

    override fun onLoggedIn() {
        Snackbar.make(findViewById(R.id.login_fragment_container),
                "User logged in.", Snackbar.LENGTH_SHORT).show()
        setResult(LOGIN_SUCCESS_CODE)
        finish()
    }
}
package io.josephtran.showstowatch.login

import android.content.Context
import io.josephtran.showstowatch.api.STWClientWrapper
import io.josephtran.showstowatch.api.STW_BASE_URL
import io.josephtran.showstowatch.shows.PREF_STW_KEY
import io.josephtran.showstowatch.shows.PREF_STW_TOKEN_KEY
import io.josephtran.showstowatch.shows.PREF_STW_USER_KEY

class LoginPresenter(val context: Context, val view: LoginView) : LoginListener {
    val client = STWClientWrapper(context)

    fun login() {
        view.showLogin(client.getLoginUrl())
    }

    override fun onUrlLoad(url: String): String {
        when (url) {
            STW_BASE_URL -> return client.getRedirectUrl()
            client.getRedirectUrl() -> return STW_BASE_URL
            else -> return ""
        }
    }

    override fun onRedirect(html: String) {
        val userString = "user\":\""
        val tokenString = "token\":\""
        if (html.contains(userString) && html.contains(tokenString)) {
            val editor = context.getSharedPreferences(PREF_STW_KEY, Context.MODE_PRIVATE).edit()
            val user = html.substring(html.indexOf(userString) + userString.length,
                    html.indexOf("\","))
            editor.putString(PREF_STW_USER_KEY, user)
            val token = html.substring(html.indexOf(tokenString) + tokenString.length,
                    html.indexOf("\"}"))
            editor.putString(PREF_STW_TOKEN_KEY, token)
            editor.apply()
            view.showHome()
        }
    }
}
package io.josephtran.showstowatch.login

interface LoginView {
    /**
     * Shows the given login url in the view.
     */
    fun showLogin(loginUrl: String)

    /**
     * Shows the home view.
     */
    fun showHome()
}

interface LoginListener {
    /**
     * Determines what should occur when the given url has been loaded in the
     * view.
     */
    fun onUrlLoad(url: String): String

    /**
     * Determines what should occur when the the view has been redirected.
     */
    fun onRedirect(html: String)
}

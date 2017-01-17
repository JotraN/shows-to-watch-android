package io.josephtran.showstowatch.show_form

import io.josephtran.showstowatch.api.STWShow

interface ShowFormView {

    /**
     * Shows the message on the view.
     *
     * @param message the message to display
     */
    fun showMessage(message: String)

    /**
     * Shows the error message on the view.
     *
     * @param errorMessage the error message to display
     */
    fun showErrorMessage(errorMessage: String)

    /**
     * Function called when the show has been handled.
     *
     * @param show the {@code STWShow} that was handled
     */
    fun onShowHandled(show: STWShow)
}
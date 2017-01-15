package io.josephtran.showstowatch.show_add

import io.josephtran.showstowatch.api.STWShow

interface ShowAddView {

    /**
     * Shows the message on the view.
     */
    fun showMessage(message: String);

    /**
     * Shows the error message on the view.
     */
    fun showErrorMessage(errorMessage: String);

    /**
     * Function called when the show has been added to STW.
     */
    fun onShowAdded(show: STWShow);
}
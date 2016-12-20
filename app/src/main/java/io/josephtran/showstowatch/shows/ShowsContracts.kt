package io.josephtran.showstowatch.shows

import io.josephtran.showstowatch.api.STWShow

interface ShowsView {
    /**
     * Adds the given list of shows to the view.
     */
    fun addShows(shows: List<STWShow>)

    /**
     * Shows an error using the view.
     */
    fun showError(error: String)

    /**
     * Shows or hides the progress view.
     */
    fun showProgress(show: Boolean)
}

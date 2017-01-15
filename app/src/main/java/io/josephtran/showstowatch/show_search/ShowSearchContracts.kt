package io.josephtran.showstowatch.show_search

import io.josephtran.showstowatch.api.TVDBShow

interface ShowSearchView {
    /**
     * Shows the tvdb shows.
     */
    fun showSearchResults(tvdbShows: List<TVDBShow>)

    /**
     * Shows or hides the progress view.
     */
    fun showProgress(show: Boolean)

    /**
     * Function called when the show's tvdb has been set.
     */
    fun onTVDBSet()
}

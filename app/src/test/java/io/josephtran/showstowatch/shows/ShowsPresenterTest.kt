package io.josephtran.showstowatch.shows

import android.content.Context
import android.content.SharedPreferences
import io.josephtran.showstowatch.api.STWShow
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ShowsPresenterTest {
    private val showsView = mock(ShowsView::class.java)
    private lateinit var presenter: ShowsPresenter

    @Before
    fun setUp() {
        doNothing().`when`(showsView).addShows(anyList<STWShow>())
        doNothing().`when`(showsView).showProgress(anyBoolean())
        doNothing().`when`(showsView).showError(anyString())
        val sharedPrefs = mock(SharedPreferences::class.java)
        val context = mock(Context::class.java)
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        `when`(sharedPrefs.getString(anyString(), anyString())).thenReturn("")
        presenter = ShowsPresenter(context, showsView)
    }

    @Test
    fun showsAndHidesProgressWhenDownloading() {
        presenter.downloadShows(ShowsPresenter.getTypeIndex(ShowsPresenter.ALL_SHOWS))
        verify(showsView).showProgress(true)
        verify(showsView).showProgress(false)
    }

    @Test
    fun downloadsShowsToView() {
        // Mock showsView to call addShows since the client will always throw an error.
        `when`(showsView.showError("Error downloading shows.")).then { showsView.addShows(emptyList()) }
        presenter.downloadShows(ShowsPresenter.getTypeIndex(ShowsPresenter.ALL_SHOWS))
        verify(showsView).addShows(anyList<STWShow>())
    }

    @Test
    fun downloadsShowsToViewForEachType() {
        // Mock showsView to call addShows since the client will always throw an error.
        `when`(showsView.showError("Error downloading shows.")).then { showsView.addShows(emptyList()) }
        presenter.downloadShows(ShowsPresenter.getTypeIndex(ShowsPresenter.ALL_SHOWS))
        presenter.downloadShows(ShowsPresenter.getTypeIndex(ShowsPresenter.IN_PROGRESS_SHOWS))
        presenter.downloadShows(ShowsPresenter.getTypeIndex(ShowsPresenter.ABANDONED_SHOWS))
        presenter.downloadShows(ShowsPresenter.getTypeIndex(ShowsPresenter.COMPLETED_SHOWS))
        verify(showsView, times(4)).addShows(anyList<STWShow>())
    }
}
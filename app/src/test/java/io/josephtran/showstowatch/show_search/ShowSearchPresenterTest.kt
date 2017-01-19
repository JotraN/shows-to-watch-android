package io.josephtran.showstowatch.show_add

import android.content.Context
import android.content.SharedPreferences
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.api.TVDBShow
import io.josephtran.showstowatch.show_search.ShowSearchPresenter
import io.josephtran.showstowatch.show_search.ShowSearchView
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ShowSearchPresenterTest {
    private val showSearchView = mock(ShowSearchView::class.java)
    private lateinit var presenter: ShowSearchPresenter

    @Before
    fun setUp() {
        doNothing().`when`(showSearchView).showProgress(anyBoolean())
        doNothing().`when`(showSearchView).showSearchResults(anyList<TVDBShow>())
        doNothing().`when`(showSearchView).onTVDBSet()
        val sharedPrefs = mock(SharedPreferences::class.java)
        val context = mock(Context::class.java)
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        `when`(sharedPrefs.getString(anyString(), anyString())).thenReturn("")
        presenter = ShowSearchPresenter(context, showSearchView)
    }

    @Test
    fun showsAndHidesProgressWhenSearching() {
        val show = STWShow(0, "Name")
        // Mock view to call show progress since the client will always throw an error.
        `when`(showSearchView.onTVDBSet()).then { showSearchView.showProgress(false) }
        presenter.searchShow(show)
        verify(showSearchView).showProgress(true)
        verify(showSearchView).showProgress(false)
    }

    @Test
    fun sendsListOfMatchingTVDBShowsToView() {
        val show = STWShow(0, "Name")
        // Mock view to call show progress since the client will always throw an error.
        `when`(showSearchView.onTVDBSet()).then { showSearchView.showSearchResults(emptyList()) }
        presenter.searchShow(show)
        verify(showSearchView).showSearchResults(anyList<TVDBShow>())
    }

    @Test
    fun sendsCallToViewWhenTVDBSet() {
        val show = STWShow(0, "Name")
        val tvdbShow = TVDBShow(0, "Name", "Banner")
        presenter.setTVDBShow(show, tvdbShow)
        verify(showSearchView).onTVDBSet()
    }
}
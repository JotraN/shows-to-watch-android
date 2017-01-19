package io.josephtran.showstowatch.show_add

import android.content.Context
import android.content.SharedPreferences
import io.josephtran.showstowatch.api.STWShow
import io.josephtran.showstowatch.show_edit.ShowEditPresenter
import io.josephtran.showstowatch.show_form.ShowFormView
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class ShowEditPresenterTest {
    private val showFormView = mock(ShowFormView::class.java)
    private lateinit var presenter: ShowEditPresenter

    @Before
    fun setUp() {
        doNothing().`when`(showFormView).showMessage(anyString())
        doNothing().`when`(showFormView).showErrorMessage(anyString())
        val sharedPrefs = mock(SharedPreferences::class.java)
        val context = mock(Context::class.java)
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        `when`(sharedPrefs.getString(anyString(), anyString())).thenReturn("")
        presenter = ShowEditPresenter(context, showFormView)
    }

    @Test
    fun sendsEditedShowToView() {
        val show = STWShow(0, "Name")
        // Mock view to call onShowHandled since the client will always throw an error.
        `when`(showFormView.showErrorMessage("java.lang.NullPointerException"))
                .then { showFormView.onShowHandled(show) }
        doNothing().`when`(showFormView).onShowHandled(show)
        presenter.editShow(show)
        verify(showFormView).onShowHandled(show)
    }

    @Test
    fun sendsDeletedShowToView() {
        val show = STWShow(0, "Name")
        // Mock view to call onShowHandled since the client will always throw an error.
        `when`(showFormView.showErrorMessage("java.lang.NullPointerException"))
                .then { showFormView.onShowHandled(show) }
        doNothing().`when`(showFormView).onShowHandled(show)
        presenter.deleteShow(show)
        verify(showFormView).onShowHandled(show)
    }
}
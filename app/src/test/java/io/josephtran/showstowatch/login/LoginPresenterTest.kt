package io.josephtran.showstowatch.shows

import android.content.Context
import android.content.SharedPreferences
import io.josephtran.showstowatch.api.STWClient
import io.josephtran.showstowatch.api.STW_BASE_URL
import io.josephtran.showstowatch.login.LoginPresenter
import io.josephtran.showstowatch.login.LoginView
import junit.framework.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito.*

class LoginPresenterTest {
    private val loginView = mock(LoginView::class.java)
    private val editor = mock(SharedPreferences.Editor::class.java)
    private lateinit var presenter: LoginPresenter

    @Before
    fun setUp() {
        doNothing().`when`(loginView).showHome()
        doNothing().`when`(loginView).showLogin(anyString())
        doNothing().`when`(editor).apply()
        `when`(editor.putString(anyString(), anyString())).thenReturn(editor)

        val sharedPrefs = mock(SharedPreferences::class.java)
        val context = mock(Context::class.java)
        `when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        `when`(sharedPrefs.getString(anyString(), anyString())).thenReturn("")
        `when`(sharedPrefs.edit()).thenReturn(editor)

        presenter = LoginPresenter(context, loginView)
    }

    @Test
    fun getsRedirectUrlGivenBaseUrl() {
        val client = STWClient("", "")
        assertEquals(client.getRedirectUrl(), presenter.onUrlLoad(STW_BASE_URL))
    }

    @Test
    fun getsBaseUrlGivenRedirectUrl() {
        val client = STWClient("", "")
        assertEquals(STW_BASE_URL, presenter.onUrlLoad(client.getRedirectUrl()))
    }

    @Test
    fun savesUserAndToken() {
        val user = "USER"
        val token = "TOKEN"
        // Can't use JSONObject since it belongs to android sdk.
        presenter.onRedirect("""{"user":"$user", "token":"$token"}""")
        verify(editor).putString(PREF_STW_USER_KEY, user)
        verify(editor).putString(PREF_STW_TOKEN_KEY, token)
        verify(editor).apply()
    }

    @Test
    fun ignoresIfNoUser() {
        val token = "TOKEN"
        presenter.onRedirect("""{"token":"$token"}""")
        verifyZeroInteractions(editor)
    }

    @Test
    fun ignoresIfNoToken() {
        val user = "USER"
        presenter.onRedirect("""{"user":"$user"}""")
        verifyZeroInteractions(editor)
    }

    @Test
    fun ignoresIfNoEndingBrace() {
        val user = "USER"
        val token = "TOKEN"
        presenter.onRedirect("""{"user":"$user", "token":"$token"""")
        verifyZeroInteractions(editor)
    }

    @Test
    fun ignoresPagesWithoutUserAndToken() {
        presenter.onRedirect("")
        verifyZeroInteractions(editor)
    }
}
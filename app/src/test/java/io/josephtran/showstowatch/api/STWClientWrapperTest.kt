package io.josephtran.showstowatch.api

import android.content.Context
import android.content.SharedPreferences
import io.josephtran.showstowatch.shows.PREF_STW_TOKEN_KEY
import io.josephtran.showstowatch.shows.PREF_STW_USER_KEY
import org.junit.Before
import org.junit.Ignore
import org.junit.Test
import org.mockito.ArgumentMatchers.*
import org.mockito.Mockito

class STWClientWrapperTest {
    val sharedPrefs = Mockito.mock(SharedPreferences::class.java)
    val context = Mockito.mock(Context::class.java)
    private lateinit var client: STWClientWrapper
    private lateinit var show: STWShow

    @Before
    fun setUp() {
        Mockito.`when`(context.getSharedPreferences(anyString(), anyInt())).thenReturn(sharedPrefs)
        Mockito.`when`(sharedPrefs.getString(eq(PREF_STW_USER_KEY), anyString()))
                .thenReturn(System.getenv("STW_USER"))
        Mockito.`when`(sharedPrefs.getString(eq(PREF_STW_TOKEN_KEY), anyString()))
                .thenReturn(System.getenv("STW_TOKEN"))
        client = STWClientWrapper(context)
        show = STWShow(null, "Show")
    }

    @Test
    fun getLoginUrl() {
        val stwClient = STWClient("", "")
        assert(client.getLoginUrl() == stwClient.getLoginUrl())
    }

    @Test
    fun getRedirectUrl() {
        val stwClient = STWClient("", "")
        assert(client.getRedirectUrl() == stwClient.getRedirectUrl())
    }

    @Test
    fun getShows() {
        client.getShows()
                .subscribe {
                    shows ->
                    assert(shows.isNotEmpty())
                }
    }

    @Test
    fun getInProgressShows() {
        client.getInProgressShows()
                .subscribe {
                    shows ->
                    assert(shows.isNotEmpty())
                }
    }

    @Test
    fun getAbandonedShows() {
        client.getAbandonedShows()
                .subscribe {
                    shows ->
                    assert(shows.isNotEmpty())
                }
    }

    @Test
    fun getCompletedShows() {
        client.getCompletedShows()
                .subscribe {
                    shows ->
                    assert(shows.isNotEmpty())
                }
    }

    @Test
    fun addShow() {
        client.addShow(show)
                .subscribe {
                    addedShow ->
                    client.deleteShow(addedShow!!)
                    assert(!addedShow.title.isNullOrEmpty())
                    assert(!addedShow.createdAt.isNullOrEmpty())
                    assert(!addedShow.updatedAt.isNullOrEmpty())
                }
    }

    @Ignore("JSONObject always returns null since android mocks it for testing")
    fun deleteShow() {
        client.deleteShow(show)
                .subscribe { result -> assert(result) }
    }

    @Test
    fun editShow() {
        client.addShow(show)
                .subscribe {
                    addedShow ->
                    val editShowTitle = "Show Edit"
                    val newShow = STWShow(addedShow!!.id, editShowTitle)
                    client.editShow(newShow).subscribe {
                        editedShow ->
                        // Delete created show.
                        client.deleteShow(editedShow!!)
                        assert(editedShow.title == editShowTitle)
                    }
                }
    }

    @Test
    fun searchTVDBShow() {
        client.addShow(show)
                .subscribe {
                    addedShow ->
                    client.searchTVDBShow(addedShow!!)
                            .subscribe {
                                tvdbResults ->
                                // Delete created show.
                                client.deleteShow(addedShow)
                                assert(!tvdbResults.isEmpty())
                            }
                }
    }
}
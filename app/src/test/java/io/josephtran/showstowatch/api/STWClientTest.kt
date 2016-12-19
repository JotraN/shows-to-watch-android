package io.josephtran.showstowatch.api

import org.junit.Before
import org.junit.Ignore
import org.junit.Test

class STWClientTest {
    private lateinit var client: STWClient
    private lateinit var show: STWShow

    @Before
    fun setUp() {
        val user = System.getenv("STW_USER");
        val token = System.getenv("STW_TOKEN")
        client = STWClient(user, token)
        show = STWShow(null, "Show")
    }

    @Test
    fun getLoginUrl() {
        assert(client.getLoginUrl() == "${STW_BASE_URL}users/sign_in")
    }

    @Test
    fun getRedirectUrl() {
        assert(client.getRedirectUrl() == "${STW_BASE_API_URL}users/request_token.json")
    }

    @Test
    fun getShows() {
        val shows = client.getShows()
        assert(!shows.isEmpty())
    }

    @Test
    fun getInProgressShows() {
        val shows = client.getInProgressShows()
        assert(!shows.isEmpty())
    }

    @Test
    fun getAbandonedShows() {
        val shows = client.getAbandonedShows()
        assert(!shows.isEmpty())
    }

    @Test
    fun getCompletedShows() {
        val shows = client.getCompletedShows()
        assert(!shows.isEmpty())
    }

    @Test
    fun addShow() {
        val addedShow = client.addShow(show)!!
        // Delete created show.
        client.deleteShow(addedShow)
        assert(!addedShow.title.isNullOrEmpty())
        assert(!addedShow.createdAt.isNullOrEmpty())
        assert(!addedShow.updatedAt.isNullOrEmpty())
    }

    @Ignore("JSONObject always returns null since android mocks it for testing")
    fun deleteShow() {
        val addedShow = client.addShow(show)!!
        assert(client.deleteShow(addedShow))
    }

    @Test
    fun editShow() {
        val addedShow = client.addShow(show)!!
        val editShowTitle = "Show Edit"
        val newShow = STWShow(addedShow.id, editShowTitle)
        val editedShow = client.editShow(newShow)!!
        // Delete created show.
        client.deleteShow(editedShow)
        assert(editedShow.title == editShowTitle)
    }

    @Test
    fun searchTVDBShow() {
        val addedShow = client.addShow(show)!!
        val tvdbResults = client.searchTVDBShow(addedShow)
        // Delete created show.
        client.deleteShow(addedShow)
        assert(!tvdbResults.isEmpty())
    }
}
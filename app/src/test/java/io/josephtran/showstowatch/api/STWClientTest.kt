package io.josephtran.showstowatch.api

import org.junit.Test

class STWClientTest {

    @Test
    fun getShows() {
        val client = STWClient()
        val shows = client.getShows()
        assert(!shows.isEmpty())
        val show = shows.first()
        assert(!show.title.isNullOrEmpty())
        assert(!show.createdAt.isNullOrEmpty())
        assert(!show.updatedAt.isNullOrEmpty())
    }

}
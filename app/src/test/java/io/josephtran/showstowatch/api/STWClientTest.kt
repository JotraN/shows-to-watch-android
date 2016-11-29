package io.josephtran.showstowatch.api

import org.junit.Test

class STWClientTest {

    @Test
    fun getShows() {
        val client = STWClient()
        assert(!client.getShows().isEmpty());
    }

}
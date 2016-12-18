package io.josephtran.showstowatch.api

import android.content.Context
import io.josephtran.showstowatch.PREF_STW_KEY
import io.josephtran.showstowatch.PREF_STW_TOKEN_KEY
import io.josephtran.showstowatch.PREF_STW_USER_KEY
import rx.Observable

class STWClientWrapper(val context: Context) {
    private val stwClient: STWClient

    init {
        val user = context.getSharedPreferences(PREF_STW_KEY, Context.MODE_PRIVATE)
                .getString(PREF_STW_USER_KEY, null)
        val token = context.getSharedPreferences(PREF_STW_KEY, Context.MODE_PRIVATE)
                .getString(PREF_STW_TOKEN_KEY, null)
        stwClient = STWClient(user, token)
    }

    fun getLoginUrl() = stwClient.getLoginUrl()

    fun getRedirectUrl() = stwClient.getRedirectUrl()

    fun getShows(): Observable<List<STWShow>> {
        return Observable.defer { Observable.just(stwClient.getShows()) }
    }

    fun addShow(show: STWShow): Observable<STWShow?> {
        return Observable.defer { Observable.just(stwClient.addShow(show)) }
    }

    fun deleteShow(show: STWShow): Observable<Boolean> {
        return Observable.defer { Observable.just(stwClient.deleteShow(show)) }
    }

    fun editShow(show: STWShow): Observable<STWShow?> {
        return Observable.defer { Observable.just(stwClient.editShow(show)) }
    }

    fun searchTVDBShow(show: STWShow): Observable<List<TVDBShow>> {
        return Observable.defer { Observable.just(stwClient.searchTVDBShow(show)) }
    }
}
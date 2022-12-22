package com.maruthimourya.games.extension

import android.widget.SearchView
import io.reactivex.rxjava3.core.Observable

fun SearchView.addRxTextWatcherSearchView(): Observable<String> {

    val flowable = Observable.create {
        setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String): Boolean {
                it.onNext(query)
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                it.onNext(newText)
                return false
            }

        })

    }

    return flowable
}
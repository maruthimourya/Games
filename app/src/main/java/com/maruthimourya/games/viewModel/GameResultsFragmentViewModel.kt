package com.maruthimourya.games.viewModel

import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maruthimourya.games.event.Event
import com.maruthimourya.games.model.ResponseData
import com.maruthimourya.games.model.ResultsItem
import com.maruthimourya.games.network.GamesListService
import com.maruthimourya.games.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameResultsFragmentViewModel: ViewModel() {

    private lateinit var service: GamesListService

    private val errorMessage = MutableLiveData<Event<String>>()

    val error: LiveData<Event<String>>
        get() = errorMessage

    private val arrayList = MutableLiveData<List<ResultsItem>>()

    val list: LiveData<List<ResultsItem>>
        get() = arrayList

    fun getResultsFromWeb() {
        service = RetrofitBuilder.statesApiService

        val apiKey  = "9d45436f87d3848d2bdcce810bacb6df57dfd134"
        val format = "json"
        var searchQuery = "name:"
        searchQuery += "avatar"
        service.getGames(apiKey , format , searchQuery).enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val body = response.body()
                        if (body!!.status_code == 1) {
                            arrayList.value = body.results
                        } else {
                            errorMessage.value = Event(body.error)
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseData>,
                t: Throwable
            ) {
                TODO("Not yet implemented")
            }

        })
    }

}
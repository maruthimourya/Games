package com.maruthimourya.games.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.maruthimourya.games.model.ResponseData
import com.maruthimourya.games.model.ResultsItem
import com.maruthimourya.games.network.GamesListService
import com.maruthimourya.games.network.RetrofitBuilder
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class GameResultsFragmentViewModel : ViewModel() {

    private lateinit var service: GamesListService
    private val mTAG = "GameResultsViewModel"

    private val _errorMessage = MutableLiveData<String>()
    val error: LiveData<String>
        get() = _errorMessage

    private val _gameInfoList = MutableLiveData<List<ResultsItem>>()
    val gameList: LiveData<List<ResultsItem>>
        get() = _gameInfoList

    fun getResultsFromWeb(apiKey: String, format: String, searchText: String, message: String) {
        service = RetrofitBuilder.statesApiService
        service.getGames(apiKey, format, searchText).enqueue(object : Callback<ResponseData> {
            override fun onResponse(
                call: Call<ResponseData>,
                response: Response<ResponseData>
            ) {
                if (response.isSuccessful) {
                    if (response.body() != null) {
                        val body = response.body()
                        if (body!!.status_code == 1) {
                            if (body.results.isNotEmpty()) {
                                _gameInfoList.value = body.results
                            } else {
                                _errorMessage.value = message
                            }
                        } else {
                            _errorMessage.value = body.error
                        }
                    }
                }
            }

            override fun onFailure(
                call: Call<ResponseData>,
                t: Throwable
            ) {
                Log.d(mTAG, "onFailure: ${t.message}")
            }
        })
    }

}

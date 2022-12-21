package com.maruthimourya.games.network

import com.maruthimourya.games.model.ResponseData
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface GamesListService {


    @GET("games/")
    fun getGames(@Query("api_key")apiKey : String, @Query("format")format : String, @Query("filter")  searchQuery : String   ): Call<ResponseData>


}
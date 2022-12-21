package com.maruthimourya.games.network

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitBuilder {

    private const val BASE_URL = "https://www.giantbomb.com/api/"

    private val retrofit: Retrofit.Builder by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
    }

    val statesApiService: GamesListService by lazy {
        retrofit
            .build()
            .create(GamesListService::class.java)
    }

}
package com.maruthimourya.games.model

data class ResponseData(
    val error: String,
    val limit: Int,
    val number_of_page_results: Int,
    val number_of_total_results: Int,
    val offset: Int,
    val results: List<ResultsItem>,
    val status_code: Int,
    val version: String
)
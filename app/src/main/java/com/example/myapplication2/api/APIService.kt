package com.example.myapplication2.api

import com.example.rakutentest.Model.Search
import retrofit2.http.GET
import retrofit2.http.Query

interface APIService {

    @GET("search")
    suspend fun getSearch(@Query("keyword") keyword: String): Search
}
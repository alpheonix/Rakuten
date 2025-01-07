package com.example.myapplication2.api

import com.example.rakutentest.Model.Search

class Repository(private val api: APIService) {
    suspend fun getSearch(query:String): Search = api.getSearch(query)

}
package com.example.myapplication2.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInterface {

    private const val BASE_URL = "https://4206121f-64a1-4256-a73d-2ac541b3efe4.mock.pstmn.io/products/"

    val api: APIService by lazy {
        Retrofit.Builder()
            .baseUrl(BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(APIService::class.java)
    }

}
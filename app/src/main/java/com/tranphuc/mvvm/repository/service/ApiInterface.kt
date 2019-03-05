package com.tranphuc.mvvm.repository.service


import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET(".")
    fun getPeople(
        @Query("results") category: String
        , @Query("nat") nat: String
    ): Call<String>
}
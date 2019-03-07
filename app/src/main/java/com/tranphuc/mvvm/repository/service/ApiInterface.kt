package com.tranphuc.mvvm.repository.service


import io.reactivex.Observable
import io.reactivex.Observer
import retrofit2.http.GET
import retrofit2.http.Query


interface ApiInterface {
    @GET(".")
    fun getPeople(
        @Query("results") category: String
        , @Query("nat") nat: String
    ): Observable<String>
}
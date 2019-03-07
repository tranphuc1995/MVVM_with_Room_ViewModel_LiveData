package com.tranphuc.mvvm.repository.service

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory


class RetrofitClient {

    private lateinit var retrofit: Retrofit

    companion object {
        private val BASE_URL: String = "https://api.randomuser.me/"
        private var instance: RetrofitClient? = null

        public fun getInstance(): RetrofitClient? {
            if (instance == null) {
                synchronized(RetrofitClient::class) {
                    instance = RetrofitClient()
                }
            }
            return instance
        }
    }

    constructor() {
        retrofit = Retrofit.Builder().baseUrl(BASE_URL)
            .addConverterFactory(ScalarsConverterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    public fun getApi(): ApiInterface {
        return retrofit.create(ApiInterface::class.java)
    }
}
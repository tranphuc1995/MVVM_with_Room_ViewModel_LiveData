package com.tranphuc.mvvm.repository.service

class ApiUtils {
    private val BASE_URL: String = "https://api.randomuser.me/"

    private var mRetrofitClient: RetrofitClient = RetrofitClient()

    public fun getApiService(): ApiInterface {
        return mRetrofitClient.getClient(BASE_URL)?.create(ApiInterface::class.java)
    }
}
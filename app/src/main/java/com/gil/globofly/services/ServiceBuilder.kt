package com.gil.globofly.services

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ServiceBuilder {


    private const val URL = "http://10.0.2.2:9000/"

    //create logger
    private val logger: HttpLoggingInterceptor = HttpLoggingInterceptor().setLevel(HttpLoggingInterceptor.Level.BODY)

    //create http client
    private val okHttp : OkHttpClient.Builder = OkHttpClient.Builder().addInterceptor(logger)

    //create retrofit builder
    private val builder: Retrofit.Builder = Retrofit.Builder().baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(okHttp.build())

    //create retrofit instance
    private val retrofit : Retrofit = builder.build()

    fun <T> buildService(serviceType: Class<T>): T{
        return retrofit.create(serviceType)
    }

}
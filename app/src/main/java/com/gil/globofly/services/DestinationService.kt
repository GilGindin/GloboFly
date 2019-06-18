package com.gil.globofly.services

import com.smartherd.globofly.models.Destination
import retrofit2.Call
import retrofit2.http.*

interface DestinationService {

    //add headers staticlly and dynamiclly
    @Headers("x-device-type: Android")
    @GET("destination")
    fun getDestinationList(@Header("Accept-Language") language: String): Call<List<Destination>>

    //query item by id
    @GET("destination/{id}")
    fun getDestination(@Path("id") id: Int) : Call<Destination>

    //add new item to server
    @POST("destination")
    fun addDestination(@Body newDestination: Destination) : Call<Destination>

    //update item in the server usin FormUrlEncoded
    @FormUrlEncoded
    @PUT("destination/{id}")
    fun updateDestination(@Path("id") id: Int ,
                          @Field("city") city: String,
                          @Field("description") desc: String,
                          @Field("country") country: String
                                            ) : Call<Destination>


    //delete item from server
    @DELETE("destination/{id}")
    fun deleteDestination(@Path("id") id: Int) : Call<Unit>
}
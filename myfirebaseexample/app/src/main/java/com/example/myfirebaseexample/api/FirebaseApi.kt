package com.example.myfirebaseexample.api

import com.example.myfirebaseexample.api.response.PostResponse
import com.example.myfirebaseexample.api.response.BiciResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path

interface FirebaseApi {
    @GET("Bicis.json")
    fun getBicis(): Call<MutableMap<String, BiciResponse>>

    @GET("Bicis/{id}.json")
    fun getBici(
        @Path("id") id: String
    ): Call<BiciResponse>

    @POST("Bicis.json")
    fun setBici(
        @Body() body: BiciResponse
    ): Call<PostResponse>
}
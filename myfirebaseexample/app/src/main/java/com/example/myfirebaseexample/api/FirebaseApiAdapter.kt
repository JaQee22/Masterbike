package com.example.myfirebaseexample.api

import com.example.myfirebaseexample.api.response.PostResponse
import com.example.myfirebaseexample.api.response.BiciResponse
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class FirebaseApiAdapter {
    private var URL_BASE = "https://servicio-tecnico-919cc-default-rtdb.firebaseio.com/"
    private val firebaseApi = Retrofit.Builder()
        .baseUrl(URL_BASE)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    fun getBicis(): MutableMap<String, BiciResponse>? {
        val call = firebaseApi.create(FirebaseApi::class.java).getBicis().execute()
        val bicis = call.body()
        return bicis
    }

    fun getBici(id: String): BiciResponse? {
        val call = firebaseApi.create(FirebaseApi::class.java).getBici(id).execute()
        val bici = call.body()
        id.also { bici?.id = it }
        return bici
    }

    fun setBici(bici: BiciResponse): PostResponse? {
        val call = firebaseApi.create(FirebaseApi::class.java).setBici(bici).execute()
        val results = call.body()
        return results
    }
}
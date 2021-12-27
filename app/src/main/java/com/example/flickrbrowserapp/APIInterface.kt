package com.example.flickrbrowserapp

import com.example.flickrbrowserapp.model.Photos
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Url

interface APIInterface {

    @GET
    fun getData(@Url url: String): Call<Photos>

}
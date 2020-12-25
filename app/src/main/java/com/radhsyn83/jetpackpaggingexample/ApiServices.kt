package com.radhsyn83.jetpackpaggingexample

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

//
// Created by Fathur Radhy
// on May 2019-05-27.
//
interface ApiServices {

    @GET("/everything?q=sports&apiKey=aa67d8d98c8e4ad1b4f16dbd5f3be348")
    fun getNews(@Query("page") page: Int, @Query("pageSize") pageSize: Int): Call<NewsModel>

}


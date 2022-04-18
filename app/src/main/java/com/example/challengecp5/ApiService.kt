package com.example.challengecp5

import retrofit2.Call
import retrofit2.http.GET

interface ApiService {
    @GET("movie/upcoming")
    fun getupcoming():Call<DataMovieRespons>
}
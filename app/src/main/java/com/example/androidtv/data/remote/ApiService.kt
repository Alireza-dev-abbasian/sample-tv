package com.example.androidtv.data.remote

import com.example.androidtv.data.model.Movie
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("top250_min.json")
    suspend fun getMovies(): List<Movie>
}
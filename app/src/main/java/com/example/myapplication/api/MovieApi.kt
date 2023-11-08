package com.example.myapplication.api

import com.example.myapplication.model.MovieList
import com.example.myapplication.utils.Constants.API_KEY
import retrofit2.http.GET
import retrofit2.http.Query
//https://api.themoviedb.org/3/trending/movie/day?language=en-US&api_key=3aa2c997ec3eb7851fa0e377b062b620
interface MovieApi {

    @GET("3/trending/movie/day?language=en-US&api_key=$API_KEY")
    suspend fun getMovies(@Query("page")page:Int):MovieList
}
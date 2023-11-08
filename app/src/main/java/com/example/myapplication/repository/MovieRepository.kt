package com.example.myapplication.repository

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.example.myapplication.api.MovieApi
import com.example.myapplication.db.MovieDatabase
import com.example.myapplication.paging.MoviePagingMediator
import com.example.myapplication.paging.MoviePagingSource
import javax.inject.Inject
@OptIn(ExperimentalPagingApi::class)
class MovieRepository @Inject constructor(private val movieApi: MovieApi,val movieDatabase: MovieDatabase) {

    fun getMovies() = Pager(config = PagingConfig(pageSize = 20, maxSize = 100),
        remoteMediator = MoviePagingMediator(movieApi,movieDatabase),
        pagingSourceFactory = {movieDatabase.movieDao().getMovies()}).flow

    suspend fun getMoviesBackground(){
        val randomNumber = (Math.random() * 10).toInt()
        val result = movieApi.getMovies(randomNumber)
       if (result != null){
           movieDatabase.movieDao().addMovies(result.results)

       }
    }
}
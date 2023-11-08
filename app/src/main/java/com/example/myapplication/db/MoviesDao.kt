package com.example.myapplication.db

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.Result

@Dao
interface MoviesDao {

    @Query("SELECT * FROM Movies")
    fun getMovies():PagingSource<Int,Result>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addMovies(movies:List<Result>)
}
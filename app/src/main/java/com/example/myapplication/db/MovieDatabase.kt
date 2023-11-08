package com.example.myapplication.db

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.myapplication.model.MovieRemoteKeys
import com.example.myapplication.model.Result

@Database(entities = [Result::class,MovieRemoteKeys::class], version = 1)
abstract class MovieDatabase:RoomDatabase() {

    abstract fun movieDao():MoviesDao
    abstract fun movieRemoteKeysDao():RemoteKeysDao
}
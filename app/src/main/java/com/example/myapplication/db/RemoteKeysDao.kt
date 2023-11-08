package com.example.myapplication.db

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.myapplication.model.MovieRemoteKeys
import com.example.myapplication.model.Result

@Dao
interface RemoteKeysDao {

    @Query("SELECT * FROM MovieRemoteKeys where id=:id")
    suspend fun getRemoteKeys(id:Int):MovieRemoteKeys

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addRemoteKeys(movies:List<MovieRemoteKeys>)
}
package com.example.myapplication.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class MovieRemoteKeys(
    @PrimaryKey(autoGenerate = false)
    val id:Int,
    val prevPage:Int?,
    val nextPage:Int?
)

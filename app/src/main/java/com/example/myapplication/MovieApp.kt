package com.example.myapplication

import android.app.Application
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.myapplication.api.MovieApi
import com.example.myapplication.db.DatabaseModule
import com.example.myapplication.db.MovieDatabase
import com.example.myapplication.di.RetrofitModule
import com.example.myapplication.repository.MovieRepository
import com.example.myapplication.worker.MovieWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class MovieApp:Application() {
    lateinit var movieRepository: MovieRepository
    override fun onCreate() {
        super.onCreate()
        initalize()
        setUpWorker()
    }

    private fun setUpWorker() {
        val constraints = Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
        val workerRequest = PeriodicWorkRequest.Builder(MovieWorker::class.java,30,TimeUnit.MINUTES)
            .setConstraints(constraints)
            .build()
       WorkManager.getInstance(this).enqueue(workerRequest)
    }

    private fun initalize() {
        var movieapi = RetrofitModule().getRetrofit().create(MovieApi::class.java)
        val movieDatabase = DatabaseModule().provideMovieDatabase(applicationContext)
        movieRepository = MovieRepository(movieapi,movieDatabase)
    }


}
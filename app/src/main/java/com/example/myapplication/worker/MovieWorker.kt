package com.example.myapplication.worker

import android.content.Context
import android.util.Log
import androidx.work.Worker
import androidx.work.WorkerParameters
import com.example.myapplication.MovieApp
import com.example.myapplication.repository.MovieRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import javax.inject.Inject

class MovieWorker @Inject constructor(val context: Context, params:WorkerParameters):Worker(context,params) {
    override fun doWork(): Result {
        Log.d("MYCODE","WORKER Called")
       CoroutineScope(Dispatchers.IO).launch {
           val repository = (context as MovieApp).movieRepository
           repository.getMoviesBackground()
       }
        return Result.success()
    }
}
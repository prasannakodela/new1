package com.example.myapplication

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.ActivityMainBinding
import com.example.myapplication.model.Result
import com.example.myapplication.paging.MoviePagingAdapter
import com.example.myapplication.viewModel.MovieViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch


@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
   /* lateinit var movieViewModel: MovieViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var moviePagingAdapter: MoviePagingAdapter*/


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        /*   recyclerView = binding.MoviesRecycler
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        moviePagingAdapter = MoviePagingAdapter(applicationContext,::onItemClicked)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = moviePagingAdapter

        lifecycleScope.launch {
            movieViewModel.movieList.collect {
                moviePagingAdapter.submitData(lifecycle, it)
            }

        }
    }
    private fun onItemClicked(response:Result){
        val bundle = Bundle()
        bundle.putString("movie",Gson().toJson(response))
      // var intent = Intent(this,MovieDetailsActivity::class.java)
       // startActivity(intent,bundle)
        var fragment = MovieDetailsFragment()
        fragment.arguments = bundle
        supportFragmentManager.beginTransaction().add(R.id.container,fragment).commit()
    }*/
    }
}
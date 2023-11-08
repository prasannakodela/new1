package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.paging.ExperimentalPagingApi
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.databinding.FragmentMovieBinding
import com.example.myapplication.model.Result
import com.example.myapplication.paging.MoviePagingAdapter
import com.example.myapplication.viewModel.MovieViewModel
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
@OptIn(ExperimentalPagingApi::class)
class MovieFragment : Fragment() {
    var _binding:FragmentMovieBinding?= null
    val binding get() = _binding!!

    lateinit var movieViewModel: MovieViewModel
    lateinit var recyclerView: RecyclerView
    lateinit var moviePagingAdapter: MoviePagingAdapter

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerView = binding.MoviesRecycler
        movieViewModel = ViewModelProvider(this).get(MovieViewModel::class.java)
        moviePagingAdapter = MoviePagingAdapter(::onItemClicked)

        recyclerView.setHasFixedSize(true)
        recyclerView.adapter = moviePagingAdapter

        lifecycleScope.launch {
            movieViewModel.movieList.collect {
                moviePagingAdapter.submitData(lifecycle, it)
            }

        }
    }
    private fun onItemClicked(response: Result){
        val bundle = Bundle()
        bundle.putString("movie", Gson().toJson(response))
        // var intent = Intent(this,MovieDetailsActivity::class.java)
        // startActivity(intent,bundle)
      findNavController().navigate(R.id.action_movieFragment_to_movieDetailsFragment,bundle)
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding =null
    }


}
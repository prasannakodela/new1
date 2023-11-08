package com.example.myapplication.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.ExperimentalPagingApi
import androidx.paging.cachedIn
import com.example.myapplication.repository.MovieRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
@OptIn(ExperimentalPagingApi::class)
class MovieViewModel @Inject constructor(private val repository: MovieRepository):ViewModel() {
    val movieList = repository.getMovies().cachedIn(viewModelScope)
}
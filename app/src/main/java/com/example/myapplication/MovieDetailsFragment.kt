package com.example.myapplication

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import coil.load
import coil.size.Scale
import com.example.myapplication.databinding.FragmentMovieDetailsBinding
import com.example.myapplication.model.Result
import com.example.myapplication.utils.Constants
import com.google.gson.Gson
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MovieDetailsFragment : Fragment() {

 var _binding:FragmentMovieDetailsBinding? = null
    private val binding get() = _binding!!
    var movie:Result? = null

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentMovieDetailsBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setInitialData()
    }

    private fun setInitialData() {
        var jsonResponse = arguments?.getString("movie")
        if (jsonResponse != null) {

                movie = Gson().fromJson<Result>(jsonResponse, Result::class.java)
           
            movie?.let {
                binding.apply {
                    originalTitle.setText("OriginalTitle  :  ${it.original_title}")
                    popularity.setText("Popularity :  ${it.popularity.toDouble().toString()}")
                    voteCount.setText("VoteCount  :  ${it.vote_count.toString()}")
                    overview.setText("OverView : ${it.overview}")
                    mediaType.setText("MediaType :  ${it.media_type}")
                    title.setText("title :  ${it.title}")
                    releaseYear.setText("ReleaseYear  :  ${it.release_date}")
                    val url = Constants.POSTER_URL +it.poster_path
                    // Glide.with(context).load(url).into(thumbNail)
                    detailPoster.load(url) {
                        crossfade(true)
                        placeholder(R.drawable.ic_launcher_foreground)
                        scale(Scale.FILL)
                    }

                }
            }
        }
    }
    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}
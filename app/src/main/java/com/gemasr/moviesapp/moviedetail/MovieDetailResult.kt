package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviResult

sealed class MovieDetailResult : MviResult {
    sealed class LoadMovieResult : MovieDetailResult() {
        data class Success(val movie: Movie, val type: MovieListType) : LoadMovieResult()
        data class Failure(val error: Throwable) : LoadMovieResult()
        object InFlight : LoadMovieResult()
    }
}
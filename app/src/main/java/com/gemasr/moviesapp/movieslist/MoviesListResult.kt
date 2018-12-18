package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviResult

sealed class MoviesListResult : MviResult {
    sealed class LoadMoviesResult : MoviesListResult() {
        data class Success(val movies: List<Movie>, val type: MovieListType, val page:Int) : LoadMoviesResult()
        data class Failure(val error: Throwable) : LoadMoviesResult()
        object InFlight : LoadMoviesResult()
    }
}
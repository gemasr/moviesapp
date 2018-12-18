package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviViewState

data class MoviesListViewState(
        val isLoading: Boolean,
        val movies: List<Movie>,
        val addedMovies: List<Movie>,
        val page:Int,
        val moviesType: MovieListType,
        val error:Throwable?
        ): MviViewState {

    companion object {
        fun idle(): MoviesListViewState {
            return MoviesListViewState(
                    isLoading = false,
                    movies = listOf(),
                    addedMovies = listOf(),
                    page = 1,
                    moviesType = MovieListType.POPULAR,
                    error = null
            )
        }
    }
}
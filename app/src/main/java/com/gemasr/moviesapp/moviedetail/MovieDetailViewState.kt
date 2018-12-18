package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviViewState

data class MovieDetailViewState(
        val isLoading: Boolean,
        val movie: Movie?,
        val type: MovieListType,
        val error:Throwable?
        ): MviViewState {

    companion object {
        fun idle(): MovieDetailViewState {
            return MovieDetailViewState(
                    isLoading = false,
                    movie = null,
                    error = null,
                    type = MovieListType.POPULAR
            )
        }
    }
}
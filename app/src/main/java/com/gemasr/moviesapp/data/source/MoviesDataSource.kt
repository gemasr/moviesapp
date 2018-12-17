package com.gemasr.moviesapp.data.source

import com.gemasr.moviesapp.data.model.Movie
import io.reactivex.Single

interface MoviesDataSource {
    fun getPopularMovies(page: Int): Single<List<Movie>>
    fun getUpcomingMovies(page: Int): Single<List<Movie>>
    fun getTopRatedMovies(page: Int): Single<List<Movie>>
}
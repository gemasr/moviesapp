package com.gemasr.moviesapp.data.source

import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.MovieListType
import io.reactivex.Single

interface MoviesDataSource {
    fun getByType(page:Int, type:MovieListType): Single<List<Movie>>
    fun getPopularMovies(page: Int): Single<List<Movie>>
    fun getUpcomingMovies(page: Int): Single<List<Movie>>
    fun getTopRatedMovies(page: Int): Single<List<Movie>>
    fun getMovieById(id: Int, type:MovieListType): Single<Movie>
}
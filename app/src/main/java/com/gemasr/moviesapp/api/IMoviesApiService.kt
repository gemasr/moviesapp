package com.gemasr.moviesapp.api

import com.gemasr.moviesapp.api.response.MoviesListResponse
import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.MovieListType
import io.reactivex.Single

interface IMoviesApiService {
    fun getPopularMovies(page: Int): Single<MoviesListResponse>
    fun getUpcomingMovies(page: Int): Single<MoviesListResponse>
    fun getTopRatedMovies(page: Int): Single<MoviesListResponse>
    fun getMovieById(movieId: Int, type:MovieListType): Single<Movie>
}
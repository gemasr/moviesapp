package com.gemasr.moviesapp.api

import com.gemasr.moviesapp.api.response.MoviesListResponse
import io.reactivex.Single

interface IMoviesApiService {
    fun getPopularMovies(page: Int): Single<MoviesListResponse>
    fun getUpcomingMovies(page: Int): Single<MoviesListResponse>
    fun getTopRatedMovies(page: Int): Single<MoviesListResponse>
}
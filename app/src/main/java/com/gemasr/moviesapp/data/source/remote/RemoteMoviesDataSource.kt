package com.gemasr.moviesapp.data.source.remote

import com.gemasr.moviesapp.api.IMoviesApiService
import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.MoviesDataSource
import io.reactivex.Single

class RemoteMoviesDataSource(val apiService: IMoviesApiService) : MoviesDataSource {

    override fun getPopularMovies(page: Int): Single<List<Movie>>{
        return apiService.getPopularMovies(page).map { it.results }
    }

    override fun getUpcomingMovies(page: Int): Single<List<Movie>>{
        return apiService.getUpcomingMovies(page).map { it.results }
    }

    override fun getTopRatedMovies(page: Int): Single<List<Movie>>{
        return apiService.getTopRatedMovies(page).map { it.results }
    }


}
package com.gemasr.moviesapp.data.source.remote

import com.gemasr.moviesapp.api.IMoviesApiService
import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.MoviesDataSource
import com.gemasr.moviesapp.data.source.local.MovieListType
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

    override fun getByType(page: Int, type: MovieListType): Single<List<Movie>> {
        return when(type){
            MovieListType.POPULAR -> getPopularMovies(page)
            MovieListType.UPCOMING -> getUpcomingMovies(page)
            MovieListType.TOP_RATED -> getTopRatedMovies(page)
        }
    }

    override fun getMovieById(movieId: Int, type: MovieListType): Single<Movie> {
        return apiService.getMovieById(movieId, type)
    }
}
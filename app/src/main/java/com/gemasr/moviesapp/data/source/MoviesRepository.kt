package com.gemasr.moviesapp.data.source

import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.LocalMoviesDataSource
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.data.source.remote.RemoteMoviesDataSource
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesRepository(val remoteMoviesDataSource: RemoteMoviesDataSource,
                       val localMoviesDataSource: LocalMoviesDataSource):MoviesDataSource {


    fun getAndCacheMovies(page:Int, type:MovieListType): List<Movie>{

        val movies = when(type){
            MovieListType.POPULAR -> remoteMoviesDataSource.getPopularMovies(page)
            MovieListType.TOP_RATED -> remoteMoviesDataSource.getTopRatedMovies(page)
            MovieListType.UPCOMING -> remoteMoviesDataSource.getUpcomingMovies(page)
        }.doAfterSuccess {
            it.forEach {movie->
                localMoviesDataSource.cacheMovie(movie, page, type)
            }
        }

        return movies.blockingGet()
    }


    override fun getByType(page: Int, type: MovieListType): Single<List<Movie>> {
        return when(type){
            MovieListType.POPULAR -> getPopularMovies(page)
            MovieListType.UPCOMING -> getUpcomingMovies(page)
            MovieListType.TOP_RATED -> getTopRatedMovies(page)
        }
    }

    override fun getPopularMovies(page: Int): Single<List<Movie>> {
        return localMoviesDataSource.getPopularMovies(page).onErrorReturn {
            getAndCacheMovies(page, MovieListType.POPULAR)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getUpcomingMovies(page: Int): Single<List<Movie>> {
        return localMoviesDataSource.getUpcomingMovies(page).onErrorReturn {
            getAndCacheMovies(page, MovieListType.UPCOMING)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getTopRatedMovies(page: Int): Single<List<Movie>> {
        return localMoviesDataSource.getTopRatedMovies(page).onErrorReturn {
            getAndCacheMovies(page, MovieListType.TOP_RATED)
        }.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
    }

    override fun getMovieById(id: Int, type: MovieListType): Single<Movie> {
        return localMoviesDataSource.getMovieById(id, type)
    }
}
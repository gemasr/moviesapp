package com.gemasr.moviesapp.data.source

import android.util.Log
import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.local.LocalMoviesDataSource
import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.data.source.remote.RemoteMoviesDataSource
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.jetbrains.anko.AnkoLogger

class MoviesRepository(val remoteMoviesDataSource: RemoteMoviesDataSource,
                       val localMoviesDataSource: LocalMoviesDataSource):MoviesDataSource, AnkoLogger {


    fun getAndCacheMovies(page:Int, type:MovieListType): List<Movie>{

        return remoteMoviesDataSource.getByType(page, type)
                .doOnSuccess {
                    it.forEach {movie->
                        localMoviesDataSource.cacheMovie(movie, page, type)
                    }
                }.doOnError{
                    Log.i("Movies error", it.message)
                }.blockingGet()

    }


    override fun getByType(page: Int, type: MovieListType): Single<List<Movie>> {
        return when(type){
            MovieListType.POPULAR -> getPopularMovies(page)
            MovieListType.UPCOMING -> getUpcomingMovies(page)
            MovieListType.TOP_RATED -> getTopRatedMovies(page)
        }
    }

    override fun getPopularMovies(page: Int): Single<List<Movie>> {
        return Observable.concat(localMoviesDataSource.getPopularMovies(page).toObservable()
                , remoteMoviesDataSource.getPopularMovies(page).toObservable())
                .filter{movies->
                    movies.isNotEmpty()
                }.first(listOf())
                .doOnSuccess { movies ->
                    movies.forEach {
                        localMoviesDataSource.cacheMovie(it, page, MovieListType.POPULAR)
                    }
                }
    }

    override fun getUpcomingMovies(page: Int): Single<List<Movie>> {
        return Observable.concat(localMoviesDataSource.getUpcomingMovies(page).toObservable()
                , remoteMoviesDataSource.getUpcomingMovies(page).toObservable())
                .filter{movies->
                    movies.isNotEmpty()
                }.first(listOf())
                .doOnSuccess { movies ->
                    movies.forEach {
                        localMoviesDataSource.cacheMovie(it, page, MovieListType.UPCOMING)
                    }
                }
    }

    override fun getTopRatedMovies(page: Int): Single<List<Movie>> {
        return Observable.concat(localMoviesDataSource.getTopRatedMovies(page).toObservable()
                , remoteMoviesDataSource.getTopRatedMovies(page).toObservable())
                .filter{movies->
                    movies.isNotEmpty()
                }.first(listOf())
                .doOnSuccess { movies ->
                    movies.forEach {
                        localMoviesDataSource.cacheMovie(it, page, MovieListType.TOP_RATED)
                    }
                }
    }

    override fun getMovieById(id: Int, type: MovieListType): Single<Movie> {
        return localMoviesDataSource.getMovieById(id, type)
    }
}
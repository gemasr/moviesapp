package com.gemasr.moviesapp.data.source.local

import com.gemasr.moviesapp.data.model.Movie
import com.gemasr.moviesapp.data.source.MoviesDataSource
import io.reactivex.Single

class LocalMoviesDataSource(val moviesDao:MoviesDao):MoviesDataSource{


    fun cacheMovie(movie:Movie, page:Int, type:MovieListType){
        val localMovie = LocalMovie.buildLocalMovieFromMovie(movie, page, type)
        moviesDao.insertMovie(localMovie)
    }

    override fun getPopularMovies(page: Int): Single<List<Movie>> {
        return moviesDao.getPopularMovies(page)
                .map { moviesList->
                    moviesList.map { localMovie ->
                        localMovie.getMovieFromLocalMovie()
                    }
                }
    }

    override fun getUpcomingMovies(page: Int): Single<List<Movie>> {
        return moviesDao.getUpcomingMovies(page)
                .map { moviesList->
                    moviesList.map { localMovie ->
                        localMovie.getMovieFromLocalMovie()
                    }
                }
    }

    override fun getTopRatedMovies(page: Int): Single<List<Movie>> {
        return moviesDao.getUpcomingMovies(page)
                .map { moviesList->
                    moviesList.map { localMovie ->
                        localMovie.getMovieFromLocalMovie()
                    }
                }
    }

    override fun getByType(page: Int, type: MovieListType): Single<List<Movie>> {
        return when(type){
            MovieListType.POPULAR -> getPopularMovies(page)
            MovieListType.UPCOMING -> getUpcomingMovies(page)
            MovieListType.TOP_RATED -> getTopRatedMovies(page)
        }
    }

    override fun getMovieById(id: Int, type:MovieListType): Single<Movie> {
        return moviesDao.findMovie(id.toString(), type.value)
                .map{ localMovie-> localMovie.getMovieFromLocalMovie() }
    }
}
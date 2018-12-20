package com.gemasr.moviesapp.api

import com.gemasr.moviesapp.api.response.MoviesListResponse
import com.gemasr.moviesapp.data.model.Movie
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface MoviesApi {

    @GET("3/movie/upcoming")
    fun getUpcomingMovies(@Query("page") page:Int): Single<MoviesListResponse>


    @GET("3/movie/top_rated")
    fun getTopRatedMovies(@Query("page") page:Int): Single<MoviesListResponse>


    @GET("3/movie/popular")
    fun getPopularMovies(@Query("page") page:Int): Single<MoviesListResponse>


    @GET("3/movie/{movieId}")
    fun getMovieById(@Path("movieId") movieId:Int): Single<Movie>
}
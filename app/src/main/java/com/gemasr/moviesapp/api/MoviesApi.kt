package com.gemasr.moviesapp.api

import com.gemasr.moviesapp.api.response.MoviesListResponse
import com.gemasr.moviesapp.data.model.Movie
import io.reactivex.Single
import retrofit2.http.Field
import retrofit2.http.GET
import retrofit2.http.Path

interface MoviesApi {

    @GET("/movie/upcoming")
    fun getUpcomingMovies(@Field("page") page:Int): Single<MoviesListResponse>


    @GET("/movie/top_rated")
    fun getTopRatedMovies(@Field("page") page:Int): Single<MoviesListResponse>


    @GET("/movie/popular")
    fun getPopularMovies(@Field("page") page:Int): Single<MoviesListResponse>


    @GET("/movie/{movieId}")
    fun getMovieById(@Path("movieId") movieId:Int): Single<Movie>
}
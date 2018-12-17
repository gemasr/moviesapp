package com.gemasr.moviesapp.api

import com.gemasr.moviesapp.BuildConfig
import com.gemasr.moviesapp.api.response.MoviesListResponse
import com.google.gson.GsonBuilder
import io.reactivex.Single
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit


class MoviesApiService : IMoviesApiService {

    val service = createService()

    private fun createService():MoviesApi {

        val httpClient = OkHttpClient.Builder()
                .readTimeout(30, TimeUnit.SECONDS)
                .addInterceptor { chain ->
                    val original = chain.request()
                    val originalHttpUrl = original.url()

                    val url = originalHttpUrl.newBuilder()
                            .addQueryParameter("apikey", BuildConfig.MOV_API_KEY)
                            .build()

                    val requestBuilder = original.newBuilder()
                            .url(url)

                    val request = requestBuilder.build()
                    chain.proceed(request)
                }
                .build()

        val gson = GsonBuilder().setLenient().create()

        return Retrofit.Builder()
                .baseUrl(BuildConfig.MOV_API_URL)
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build()
                .create(MoviesApi::class.java)

    }


    override fun getPopularMovies(page:Int): Single<MoviesListResponse> {
        return service.getPopularMovies(page)
    }

    override fun getUpcomingMovies(page:Int): Single<MoviesListResponse>{
        return service.getUpcomingMovies(page)
    }

    override fun getTopRatedMovies(page:Int): Single<MoviesListResponse>{
        return service.getTopRatedMovies(page)
    }




}
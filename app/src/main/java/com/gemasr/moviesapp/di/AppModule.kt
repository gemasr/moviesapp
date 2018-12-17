package com.gemasr.moviesapp.di

import android.arch.persistence.room.Room
import com.gemasr.moviesapp.api.IMoviesApiService
import com.gemasr.moviesapp.api.MoviesApiService
import com.gemasr.moviesapp.data.source.MoviesRepository
import com.gemasr.moviesapp.data.source.local.LocalMoviesDataSource
import com.gemasr.moviesapp.data.source.local.MoviesDao
import com.gemasr.moviesapp.data.source.local.MoviesDatabase
import com.gemasr.moviesapp.data.source.remote.RemoteMoviesDataSource
import org.koin.android.ext.koin.androidApplication
import org.koin.dsl.module.Module
import org.koin.dsl.module.module

val moviesAppModule: Module = module {

    single{ MoviesApiService() as IMoviesApiService }
    single{ RemoteMoviesDataSource(get()) }
    single{
        Room.databaseBuilder(androidApplication(), MoviesDatabase::class.java, "movies-db")
                .build()
    }
    single{ get<MoviesDatabase>().MoviesDao() }
    single{ LocalMoviesDataSource(get())}
    single{ MoviesRepository(get(), get())}
}
package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviIntent

sealed class MoviesListIntent: MviIntent {
    object InitialIntent:MoviesListIntent()
    data class LoadMoviesIntent(val page:Int, val type:MovieListType):MoviesListIntent()
}
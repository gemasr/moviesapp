package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.mvibase.MviAction

sealed class MoviesListAction: MviAction {

    data class LoadMoviesAction(val page: Int, val type:MovieListType):MoviesListAction()
}
package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.source.MoviesRepository
import io.reactivex.Observable
import io.reactivex.ObservableSource
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MoviesListProcessorHolder(val repository: MoviesRepository) {

    private val loadMoviesProcessor =
            ObservableTransformer<MoviesListAction.LoadMoviesAction, MoviesListResult.LoadMoviesResult> { actions ->
                actions.flatMap { action ->
                    repository.getByType(action.page, action.type)
                            .toObservable()
                            .map { movies -> MoviesListResult.LoadMoviesResult.Success(movies, action.type, action.page) }
                            .cast(MoviesListResult.LoadMoviesResult::class.java)
                            .onErrorReturn(MoviesListResult.LoadMoviesResult::Failure)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .startWith(MoviesListResult.LoadMoviesResult.InFlight)
                }
            }


    internal var actionProcessor =
            ObservableTransformer<MoviesListAction, MoviesListResult> { actions ->
                actions.publish { shared ->
                            shared.ofType(MoviesListAction.LoadMoviesAction::class.java)
                                    .compose(loadMoviesProcessor)
                                    .cast(MoviesListResult::class.java)
                }
            }




}
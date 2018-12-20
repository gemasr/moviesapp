package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.source.MoviesRepository
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class MovieDetailProcessor(val repository: MoviesRepository) {

    private val loadMovieProcessor =
            ObservableTransformer<MovieDetailAction.LoadMovieDetailAction, MovieDetailResult.LoadMovieResult> { actions ->
                actions.flatMap { action ->
                    repository.getMovieById(action.id, action.type)
                            .toObservable()
                            .map { movie -> MovieDetailResult.LoadMovieResult.Success(movie, action.type) }
                            .cast(MovieDetailResult.LoadMovieResult::class.java)
                            .onErrorReturn(MovieDetailResult.LoadMovieResult::Failure)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .startWith(MovieDetailResult.LoadMovieResult.InFlight)
                }
            }


    internal var actionProcessor =
            ObservableTransformer<MovieDetailAction, MovieDetailResult> { actions ->
                actions.publish { shared ->
                            shared.ofType(MovieDetailAction.LoadMovieDetailAction::class.java)
                                    .compose(loadMovieProcessor)
                                    .cast(MovieDetailResult::class.java)

                }
            }




}
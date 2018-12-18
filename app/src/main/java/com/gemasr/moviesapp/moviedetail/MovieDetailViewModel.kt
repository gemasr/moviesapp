package com.gemasr.moviesapp.movieslist

import com.gemasr.moviesapp.data.source.local.MovieListType
import com.gemasr.moviesapp.ext.notOfType
import com.gemasr.moviesapp.mvibase.MviViewModel
import io.reactivex.Observable
import io.reactivex.ObservableTransformer
import io.reactivex.functions.BiFunction
import io.reactivex.subjects.PublishSubject

class MovieDetailViewModel(
        private val actionProcessorHolder: MovieDetailProcessor
): MviViewModel<MovieDetailIntent, MovieDetailViewState> {

    private val intentsSubject: PublishSubject<MovieDetailIntent> = PublishSubject.create()
    private val statesObservable: Observable<MovieDetailViewState> = compose()


    override fun processIntents(intents: Observable<MovieDetailIntent>) {
        intents.subscribe(intentsSubject)
    }

    override fun states(): Observable<MovieDetailViewState> = statesObservable


    private fun compose(): Observable<MovieDetailViewState> {
        return intentsSubject
                .map(this::actionFromIntent)
                .compose(actionProcessorHolder.actionProcessor)
                .scan(MovieDetailViewState.idle(), reducer)
                .distinctUntilChanged()
                .replay(1)
                .autoConnect(0)
    }



    private fun actionFromIntent(intent: MovieDetailIntent): MovieDetailAction {
        return when (intent) {
            is MovieDetailIntent.LoadMovieIntent -> MovieDetailAction.LoadMovieDetailAction(intent.id, intent.type)
        }
    }



    companion object {
        private val reducer = BiFunction { previousState: MovieDetailViewState, result: MovieDetailResult ->
            when (result) {
                is MovieDetailResult.LoadMovieResult -> when (result) {
                    is MovieDetailResult.LoadMovieResult.Success -> {
                        previousState.copy(
                                isLoading = false,
                                movie = result.movie,
                                type = result.type
                        )
                    }
                    is MovieDetailResult.LoadMovieResult.Failure -> previousState.copy(isLoading = false, error = result.error)
                    is MovieDetailResult.LoadMovieResult.InFlight -> previousState.copy(isLoading = true)
                }

            }
        }
    }



}
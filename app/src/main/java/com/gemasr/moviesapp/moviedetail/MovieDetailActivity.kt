package com.gemasr.moviesapp.movieslist

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.gemasr.moviesapp.BuildConfig
import com.gemasr.moviesapp.R
import com.gemasr.moviesapp.ext.bind
import com.gemasr.moviesapp.mvibase.MviView
import com.squareup.picasso.Picasso
import io.reactivex.Observable
import android.os.Build
import com.squareup.picasso.Callback
import io.reactivex.disposables.CompositeDisposable
import org.koin.android.viewmodel.ext.android.viewModel
import java.lang.Exception


class MovieDetailActivity : AppCompatActivity(), MviView<MovieDetailIntent, MovieDetailViewState> {

    val mViewModel by viewModel<MovieDetailViewModel>()

    val mImage by bind<ImageView>(R.id.detail_movie_image)
    val mTitle by bind<TextView>(R.id.detail_movie_title)
    val mRating by bind<TextView>(R.id.detail_movie_average_votes)
    val mOverview by bind<TextView>(R.id.detail_movie_overview)
    val mYear by bind<TextView>(R.id.detail_movie_release_year)

    private val disposables = CompositeDisposable()

    companion object {
        const val EXTRA_MOVIE_ITEM_ID = "EXTRA_MOVIE_ITEM_ID"
        const val EXTRA_MOVIE_ITEM_IMAGE = "EXTRA_MOVIE_ITEM_IMAGE"
        const val EXTRA_MOVIE_IMAGE_TRANSITION_NAME = "EXTRA_MOVIE_IMAGE_TRANSITION_NAME"
        const val EXTRA_MOVIE_TYPE = "EXTRA_MOVIE_TYPE"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_movie_detail)

        val extras = intent.extras

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val imageTransitionName = extras.getString(EXTRA_MOVIE_IMAGE_TRANSITION_NAME)
            mImage.transitionName = imageTransitionName
        }

        mViewModel.movieId = extras.getInt(EXTRA_MOVIE_ITEM_ID)
        mViewModel.getTypeFromInt(extras.getInt(EXTRA_MOVIE_TYPE))

        if (mViewModel.movieId == null){
            finish()
        }

        Picasso.get()
                .load(extras.getString(EXTRA_MOVIE_ITEM_IMAGE))
                .noFade()
                .into(mImage, object : Callback {
                    override fun onSuccess() {
                        supportStartPostponedEnterTransition()
                    }

                    override fun onError(e: Exception?) {
                        supportStartPostponedEnterTransition()
                    }
                })

    }


    override fun onStart() {
        super.onStart()

        bind()
    }

    override fun onStop() {
        super.onStop()

        disposables.clear()
    }

    private fun bind() {
        disposables.add(mViewModel.states().subscribe(this::render))
        mViewModel.processIntents(intents())
    }

    override fun intents(): Observable<MovieDetailIntent> {
        return Observable.just(MovieDetailIntent.LoadMovieIntent(mViewModel.movieId!!, mViewModel.type))
    }

    override fun render(state: MovieDetailViewState) {
        state.movie?.let{
            Picasso.get().load(BuildConfig.MOV_API_IMAGES_URL+it.posterpath).into(mImage)

            mTitle.text = it.title
            mOverview.text = it.overview
            mRating.text = "${it.voteAverage}/10"
            mYear.text = it.getYear().toString()
        }
    }

}

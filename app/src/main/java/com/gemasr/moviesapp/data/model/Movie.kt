package com.gemasr.moviesapp.data.model

import com.google.gson.annotations.SerializedName

data class Movie (val id: Int,
                  val posterpath:String?,
                  val adult:Boolean,
                  val overview:String,
                  @SerializedName("release_date") val releaseDate:String,
                  @SerializedName("genre_ids") val genreIds:List<String>,
                  @SerializedName("original_title") val originalTitle: String,
                  @SerializedName("original_language") val originalLanguage: String,
                  val title:String,
                  @SerializedName("backdrop_path") val backdropPath:String?,
                  val popularity:Float,
                  @SerializedName("vote_count")val voteCount: Int,
                  val video:Boolean,
                  @SerializedName("vote_average") val voteAverage: Float
                  )
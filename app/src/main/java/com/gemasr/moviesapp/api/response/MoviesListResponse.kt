package com.gemasr.moviesapp.api.response

import com.gemasr.moviesapp.data.model.Movie

data class MoviesListResponse(val page:Int,
                              val results:List<Movie>,
                              val total_results: Int,
                              val total_pages: Int
                              )

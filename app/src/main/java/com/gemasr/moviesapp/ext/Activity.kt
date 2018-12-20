package com.gemasr.moviesapp.ext

import android.app.Activity
import android.view.View
import androidx.annotation.IdRes

fun <T : View> Activity.bind(@IdRes res : Int) : Lazy<T> {
    @Suppress("UNCHECKED_CAST")
    return lazy(LazyThreadSafetyMode.NONE){ findViewById(res) as T }
}
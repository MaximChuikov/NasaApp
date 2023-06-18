package com.maximchuikov.nasaapp.util

import android.util.Log
import androidx.appcompat.widget.AppCompatImageView
import com.bumptech.glide.Glide

fun AppCompatImageView.loadUrl(url: String) {
    Log.d("Image URL", url)
    Glide.with(this)
        .load(url)
        .centerCrop()
        .into(this)
}
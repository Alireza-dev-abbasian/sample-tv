package com.example.androidtv.data.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Category(
    val id: Long,
    val genre: String,
    val movies: List<Movie>
) : Parcelable

package com.example.androidtv.data.model

import android.os.Parcelable
import kotlinx.parcelize.IgnoredOnParcel
import kotlinx.parcelize.Parcelize

@Parcelize
data class Movie(
    val actors: List<String>,
    val desc: String,
    val directors: List<String>,
    val genre: List<String>,
    val image_url: String,
    val imdb_url: String,
    val name: String,
    val rating: Double,
    val thumb_url: String,
    val year: Int
) : Parcelable {

    @IgnoredOnParcel
    var categoryId: Long = -1
}
package com.example.androidtv.ui.home

import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.content.ContextCompat
import androidx.leanback.widget.BaseCardView
import androidx.leanback.widget.ImageCardView
import androidx.leanback.widget.Presenter
import com.bumptech.glide.RequestManager
import com.bumptech.glide.request.RequestOptions
import com.example.androidtv.R
import com.example.androidtv.data.model.Movie

class PosterPresenter constructor(val glide: RequestManager) : Presenter() {

    override fun onCreateViewHolder(parent: ViewGroup): ViewHolder {
        val imageCardView = ImageCardView(parent.context).apply {
            isFocusable = true
            isFocusableInTouchMode = true
            setMainImageScaleType(ImageView.ScaleType.FIT_XY)
            cardType = BaseCardView.CARD_TYPE_INFO_UNDER
            with(mainImageView) {
                val posterWidth = parent.resources.getDimension(R.dimen.poster_width).toInt()
                val posterHeight = parent.resources.getDimension(R.dimen.poster_height).toInt()
                layoutParams = BaseCardView.LayoutParams(posterWidth, posterHeight)
            }
        }
        return ViewHolder(imageCardView)
    }

    override fun onBindViewHolder(viewHolder: ViewHolder, item: Any?) {
        val movie = item as Movie
        with(viewHolder.view as ImageCardView) {

            val posterWidth = resources.getDimension(R.dimen.poster_width).toInt()
            val posterHeight = resources.getDimension(R.dimen.poster_height).toInt()

            glide.load(movie.image_url)
                .placeholder(com.google.android.material.R.drawable.tooltip_frame_dark)
                .apply(RequestOptions().override(posterWidth, posterHeight))
                .into(this.mainImageView)
            titleText = movie.name
        }
    }

    override fun onUnbindViewHolder(viewHolder: ViewHolder) {
        with(viewHolder.view as ImageCardView) {
            mainImage = null
        }
    }
}
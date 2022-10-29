package com.example.androidtv.ui.home

import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.view.View
import androidx.core.content.ContextCompat
import androidx.fragment.app.viewModels
import androidx.leanback.app.BackgroundManager
import androidx.leanback.app.BrowseSupportFragment
import androidx.leanback.widget.*
import androidx.lifecycle.lifecycleScope
import androidx.palette.graphics.Palette
import com.bumptech.glide.RequestManager
import com.example.androidtv.R
import com.example.androidtv.data.model.Category
import com.example.androidtv.ui.header.CustomTitleView
import com.example.androidtv.ui.header.OnTitleReadyListener
import com.example.androidtv.util.network.Resource
import com.example.androidtv.viewmodel.HomeViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject


@AndroidEntryPoint
class HomeFragment : BrowseSupportFragment() {

    companion object {
        fun newInstance() = HomeFragment()
    }

    private val viewModel: HomeViewModel by viewModels()
    private val backgroundManager by lazy {
        BackgroundManager.getInstance(requireActivity()).apply {
            attach(requireActivity().window)
        }
    }

    @Inject
    lateinit var glide: RequestManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (savedInstanceState == null) {
            prepareEntranceTransition()
        }

        headersState = HEADERS_DISABLED
        backgroundManager.color = resources.getColor(R.color.blue_grey_900, null)
        brandColor = ContextCompat.getColor(requireContext(), R.color.amber_800)
        setTitleView(R.layout.custom_titleview, object : OnTitleReadyListener {
            override fun onReady(v: View?) {}
        })

        setDynamicBackground()
        observeData()
    }

    fun setTitleView(resId: Int, onTitleReadyListener: OnTitleReadyListener?) {
        onTitleReadyListener?.let {
            titleView = CustomTitleView(requireContext())
            (titleView as CustomTitleView).addView(resId, it)
        }
    }

    private fun setDynamicBackground() {
        setOnItemViewClickedListener { itemViewHolder, _, _, _ ->
            if (itemViewHolder?.view != null) {
                val bitmapDrawable =
                    (itemViewHolder.view as ImageCardView).mainImageView.drawable as? BitmapDrawable
                if (bitmapDrawable != null) {
                    Palette.from(bitmapDrawable.bitmap).generate { palette ->
                        (palette?.vibrantSwatch ?: palette?.dominantSwatch)?.let { swatch ->
                            backgroundManager.color = swatch.rgb
                        }
                    }
                }
            }
        }
    }

    private fun observeData() {
        lifecycleScope.launch {
            viewModel.moviesResponse.collect { resource ->
                when (resource) {
                    is Resource.Loading -> {
                    }
                    is Resource.Success -> {
                        displayData(resource.data)
                        startEntranceTransition()
                    }
                    is Resource.Error -> {

                    }
                }
            }
        }
    }

    private fun displayData(categories: List<Category>?) {
        if (categories != null) {
            val adapter = ArrayObjectAdapter(ListRowPresenter())

            for (category in categories) {
                val headerItem = HeaderItem(category.id, category.genre)
                val rowAdapter = ArrayObjectAdapter(PosterPresenter(glide))
                for (movie in category.movies) {
                    rowAdapter.add(movie)
                }
                adapter.add(ListRow(headerItem, rowAdapter))
            }

            this.adapter = adapter
        }
    }

}
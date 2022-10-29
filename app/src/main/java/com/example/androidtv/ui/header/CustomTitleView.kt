package com.example.androidtv.ui.header

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.RelativeLayout
import androidx.leanback.widget.TitleViewAdapter
import com.example.androidtv.R

class CustomTitleView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
) : RelativeLayout(context, attrs, defStyleAttr), TitleViewAdapter.Provider {

    var view: View? = null

    init {
        view = LayoutInflater.from(context).inflate(R.layout.custom_titleview, this)
    }

    private val titleViewAdapter = object : TitleViewAdapter() {
        override fun getSearchAffordanceView(): View? {
            return null
        }
    }

    override fun getTitleViewAdapter(): TitleViewAdapter {
        return titleViewAdapter
    }


    fun addView(resId: Int, onTitleReadyListener: OnTitleReadyListener) {
        onTitleReadyListener.onReady(view)
    }
}
package com.example.androidtv.ui.base

import android.R
import android.app.Application
import androidx.multidex.MultiDex
import dagger.hilt.android.HiltAndroidApp
import io.github.inflationx.calligraphy3.CalligraphyConfig
import io.github.inflationx.calligraphy3.CalligraphyInterceptor
import io.github.inflationx.viewpump.ViewPump


@HiltAndroidApp
class App : Application() {

    override fun onCreate() {
        super.onCreate()
        MultiDex.install(this)
        initFont()
    }

    private fun initFont() {
        ViewPump.init(
            ViewPump.builder().addInterceptor(
                CalligraphyInterceptor(
                    CalligraphyConfig.Builder()
                        .setDefaultFontPath("fonts/b_nazanin.ttf")
                        .build()
                )
            ).build()
        )
    }
}
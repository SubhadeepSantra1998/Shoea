package com.subha.optymize

import android.app.Application
import dagger.hilt.android.HiltAndroidApp


@HiltAndroidApp
class ShoeaApp : Application() {
    companion object {
        lateinit var instance: ShoeaApp
            private set
    }
}
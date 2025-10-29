package com.wtc.crm

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * WTC Application - Classe principal da aplicação
 */
@HiltAndroidApp
class WTCApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }
}


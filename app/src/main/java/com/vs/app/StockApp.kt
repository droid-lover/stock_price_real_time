package com.vs.app

import androidx.multidex.MultiDexApplication

/**
 * Created by Sachin
 */
class StockApp : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        instance = this
    }

    companion object {
        private val TAG = StockApp::class.java.name
        @get:Synchronized
        var instance: StockApp? = null
        private val MAX_HEAP_SIZE = Runtime.getRuntime().maxMemory().toInt()

    }
}
